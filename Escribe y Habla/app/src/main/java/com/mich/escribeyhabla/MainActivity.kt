package com.mich.escribeyhabla

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var speechSpeedSeekBar: SeekBar? = null
    private var isSpeaking: Boolean = false
    private var partesTexto: MutableList<String> = mutableListOf()
    private var posicionActual: Int = 0
    private lateinit var scrollView: ScrollView
    private lateinit var etMessage: EditText

    private var voices: MutableList<Voice>? = null
    private var selectedVoice: Voice? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity", "onCreate")

        inicializarTextToSpeech()
        configurarBotonClic()
        configurarSeekBarVelocidad()
        obtenerVocesEspanol()
        configurarBotonSeleccionVoz()

        // scrollView = findViewById(R.id.scrollView)
        val nestedScrollView = findViewById<NestedScrollView>(R.id.nestedScrollView)
        val bottomContainer = findViewById<RelativeLayout>(R.id.bottomContainer)

        nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY && bottomContainer.translationY == 0f) {
                // Ocultar el botón al hacer scroll hacia abajo
                bottomContainer.animate().translationY(bottomContainer.height.toFloat())
            } else if (scrollY < oldScrollY && bottomContainer.translationY == bottomContainer.height.toFloat()) {
                // Mostrar el botón al hacer scroll hacia arriba
                bottomContainer.animate().translationY(0f)
            }
        }

        etMessage = findViewById(R.id.etMessage)
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Escribe y Habla"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun inicializarTextToSpeech() {
        try {
            tts = TextToSpeech(this) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val locale = Locale.getDefault()
                    if (tts?.isLanguageAvailable(locale) == TextToSpeech.LANG_AVAILABLE) {
                        tts?.language = locale
                        tts?.setSpeechRate(1.5f)
                    } else {
                        Log.e("MainActivity", "Idioma no soportado: ${locale.language}")
                    }
                } else {
                    Log.e("MainActivity", "Error en la inicialización de TextToSpeech")
                }
            }

            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}

                override fun onDone(utteranceId: String?) {
                    runOnUiThread {
                        when (posicionActual) {
                            partesTexto.size - 1 -> detenerHabla()
                            else -> avanzarParte()
                        }
                    }
                }

                override fun onError(utteranceId: String?) {}
            })
        } catch (e: Exception) {
            Log.e("MainActivity", "Error en inicializarTextToSpeech: ${e.message}")
        }
    }

    private fun configurarBotonClic() {
        findViewById<Button>(R.id.btnPlay).setOnClickListener {
            if (!isSpeaking) {
                hablar()
            } else {
                detenerHabla()
            }
        }
    }

    private fun configurarSeekBarVelocidad() {
        speechSpeedSeekBar = findViewById(R.id.seekBarSpeed)

        val progresoPuntoMedio = 50
        speechSpeedSeekBar?.progress = progresoPuntoMedio

        speechSpeedSeekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val velocidad = (0.5f + (progress / 100f)).coerceIn(0.1f, 2.0f)
                establecerVelocidadHabla(velocidad)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun hablar() {
        val mensaje: String = etMessage.text.toString().trim()
        if (mensaje.isNotEmpty()) {
            tts?.setSpeechRate(1.5f) // Ajustar la velocidad aquí
            partirTexto(mensaje)
            reproducirPartes()
            isSpeaking = true
        } else {
            tts?.speak("Introduce un texto por favor", TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    private fun partirTexto(mensaje: String) {
        partesTexto.clear()

        val palabras = mensaje.split("\\s+".toRegex())
        val palabrasPorParte = 30

        for (i in 0 until palabras.size step palabrasPorParte) {
            val inicio = i
            val fin = minOf(i + palabrasPorParte, palabras.size)
            val parte = palabras.subList(inicio, fin).joinToString(" ")
            partesTexto.add(parte)
        }
    }

    private fun reproducirPartes() {
        try {
            posicionActual = 0
            val params = Bundle()
            params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "parte")

            // Iniciar la reproducción de la primera parte
            tts?.speak(partesTexto[0], TextToSpeech.QUEUE_FLUSH, params, "parte")

            // Resaltar la primera parte
            resaltarParteActual()

            // Calcular el desplazamiento inicial
            val offsetInicial = 0

            // Ajustar el desplazamiento inicial
            scrollView.post {
                scrollView.smoothScrollTo(0, offsetInicial)
                etMessage.requestFocus() // Mantener el foco en el EditText
            }

            // Monitorear el progreso de la reproducción y ajustar el desplazamiento
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}

                override fun onDone(utteranceId: String?) {
                    runOnUiThread {
                        avanzarParte()
                    }
                }

                override fun onError(utteranceId: String?) {}
            })
        } catch (e: Exception) {
            Log.e("MainActivity", "Error en reproducirPartes: ${e.message}")
        }
    }

    // Método para calcular el desplazamiento para mantener la parte en el centro
    private fun calcularDesplazamiento(parte: String): Int {
        val layout = etMessage.layout
        val offset = layout.getOffsetForHorizontal(layout.getLineForOffset(0), 0f)

        return offset
    }

    private fun avanzarParte() {
        try {
            posicionActual++
            if (posicionActual < partesTexto.size) {
                resaltarParteActual()

                val params = Bundle()
                params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "parte")

                if (posicionActual == 1) {
                    tts?.speak(partesTexto[posicionActual], TextToSpeech.QUEUE_FLUSH, params, "parte")
                } else {
                    tts?.speak(partesTexto[posicionActual], TextToSpeech.QUEUE_ADD, params, "parte")
                }
            } else {
                detenerHabla()
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error en avanzarParte: ${e.message}")
        }
    }

    private fun detenerHabla() {
        try {
            tts?.stop()
            isSpeaking = false
            partesTexto.clear()
            posicionActual = 0
        } catch (e: Exception) {
            Log.e("MainActivity", "Error en detenerHabla: ${e.message}")
        }
    }

    private fun resaltarParteActual() {
        try {
            val textoCompleto = partesTexto.joinToString(" ")
            val posicion = textoCompleto.indexOf(partesTexto[posicionActual])

            if (posicion >= 0) {
                val resaltado = SpannableString(textoCompleto)
                resaltado.setSpan(
                    BackgroundColorSpan(ContextCompat.getColor(this, android.R.color.darker_gray)),
                    posicion,
                    posicion + partesTexto[posicionActual].length,
                    0
                )
                etMessage.setText(resaltado, TextView.BufferType.SPANNABLE)

                // Calcular el centro de la parte resaltada
                val centroResaltado = posicion + partesTexto[posicionActual].length / 2

                // Obtener la posición del cursor en la parte resaltada
                val cursorPosition = posicion + etMessage.selectionStart

                // Configurar la selección del cursor
                etMessage.setSelection(cursorPosition)

                // Calcular la posición de desplazamiento para mantener el centro en la pantalla
                val offset = centroResaltado - scrollView.height / 2

                // Ajustar la posición de desplazamiento para asegurarse de que la parte resaltada sea visible
                val maxOffset = etMessage.layout.getLineBottom(etMessage.layout.lineCount - 1) - scrollView.height
                val finalOffset = offset.coerceIn(0, maxOffset)

                // Ajustar la posición de desplazamiento utilizando OnPreDrawListener
                scrollView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        scrollView.viewTreeObserver.removeOnPreDrawListener(this)

                        // Desplazar hacia arriba automáticamente para mantener la parte resaltada visible
                        scrollView.smoothScrollTo(0, finalOffset)

                        etMessage.requestFocus() // Mantener el foco en el EditText
                        return true
                    }
                })
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error en resaltarParteActual: ${e.message}")
        }
    }



    private fun establecerVelocidadHabla(velocidad: Float) {
        try {
            tts?.setSpeechRate(velocidad)
        } catch (e: Exception) {
            Log.e("MainActivity", "Error en establecerVelocidadHabla: ${e.message}")
        }
    }

    override fun onInit(status: Int) {
        try {
            if (status == TextToSpeech.SUCCESS) {
                establecerIdiomaTts(Locale("es"))
                obtenerVocesEspanol()
            } else {
                manejarFalloInicializacionTts()
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error en onInit: ${e.message}")
        }
    }
    private fun obtenerVocesEspanol(): List<Voice> {
        val vocesDisponibles = mutableListOf<Voice>()
        tts?.voices?.forEach { voz ->
            if (voz.locale.language == "es") {
                vocesDisponibles.add(voz)
            }
        }
        return vocesDisponibles
    }


    private fun configurarBotonSeleccionVoz() {
        findViewById<Button>(R.id.btnSelectVoice).setOnClickListener {
            mostrarListaVoces()
        }
    }

    private fun mostrarListaVoces() {
        val vocesDisponibles = obtenerVocesEspanol()
        val nombresVoces = vocesDisponibles.map { it.name }.toTypedArray()

        AlertDialog.Builder(this)
            .setTitle("Selecciona una voz en español")
            .setItems(nombresVoces) { _, which ->
                val vozSeleccionada = vocesDisponibles[which]
                selectedVoice = vozSeleccionada  // Asignar la voz seleccionada a selectedVoice
                establecerVoz(vozSeleccionada)
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun establecerVoz(vozSeleccionada: Voice) {
        try {
            tts?.voice = vozSeleccionada
        } catch (e: Exception) {
            Log.e("MainActivity", "Error al establecer la voz: ${e.message}")
        }
    }


    private fun establecerIdiomaTts(locale: Locale) {
        try {
            tts?.language = locale
        } catch (e: Exception) {
            Log.e("MainActivity", "Error en establecerIdiomaTts: ${e.message}")
        }
    }

    private fun manejarFalloInicializacionTts() {
        try {
            findViewById<Button>(R.id.btnPlay).isEnabled = false
        } catch (e: Exception) {
            Log.e("MainActivity", "Error en manejarFalloInicializacionTts: ${e.message}")
        }
    }

    override fun onPause() {
        try {
            Log.d("MainActivity", "onPause")
            super.onPause()
        } catch (e: Exception) {
            Log.e("MainActivity", "Error en onPause: ${e.message}")
        }
    }

    override fun onStop() {
        try {
            Log.d("MainActivity", "onStop")
            super.onStop()
        } catch (e: Exception) {
            Log.e("MainActivity", "Error en onStop: ${e.message}")
        }
    }

    override fun onResume() {
        try {
            Log.d("MainActivity", "onResume")
            super.onResume()
        } catch (e: Exception) {
            Log.e("MainActivity", "Error en onResume: ${e.message}")
        }
    }

    override fun onDestroy() {
        try {
            Log.d("MainActivity", "onDestroy")
            if (!isFinishing) {
                tts?.stop()
                tts?.shutdown()
            }
            super.onDestroy()
        } catch (e: Exception) {
            Log.e("MainActivity", "Error en onDestroy: ${e.message}")
        }
    }
}
