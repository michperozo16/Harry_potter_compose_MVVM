package com.example.grabadoraitg
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var grabadora: MediaRecorder? = null
    private var ruta: String? = null
    private var imgGrabar: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var recordingProgress: Int = 0
    private var recordingDuration: Int = 60 // Duración de la grabación en segundos
    private var mediaPlayer: MediaPlayer? = null

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgGrabar = findViewById(R.id.btnRecord)
        progressBar = findViewById(R.id.progressBar)

        // Inicializar mediaPlayer
        mediaPlayer = MediaPlayer()

        // Solicitar permisos de escritura y lectura
                if (ContextCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.RECORD_AUDIO
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO
                        ), 1000
                    )
                }
    }


        fun grabar(view: View) {
        if (grabadora == null) {
            ruta = (externalCacheDir?.absolutePath + "/grabacion.mp3")

            // Asegúrate de tener permisos WRITE_EXTERNAL_STORAGE
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Añade este bloque para crear el archivo si no existe
                val archivo = File(ruta)
                if (!archivo.exists()) {
                    try {
                        archivo.createNewFile()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        // Manejar el error al crear el archivo
                        return
                    }
                }

                grabadora = MediaRecorder()
                grabadora?.setAudioSource(MediaRecorder.AudioSource.MIC)
                grabadora?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                grabadora?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                grabadora?.setOutputFile(ruta)

                grabadora?.release()
                mediaPlayer?.release()

                try {
                    grabadora?.prepare()
                    grabadora?.start()
                    imgGrabar?.setBackgroundColor(Color.RED)
                    Toast.makeText(applicationContext, "Grabando...", Toast.LENGTH_SHORT).show()

                    // Inicializa la barra de progreso
                    recordingProgress = 0
                    progressBar?.max = recordingDuration


                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "Error al preparar la grabación", Toast.LENGTH_SHORT).show()
                    detenerGrabacion()
                }
            } else {
                // Solicitar permisos si no están concedidos
                val REQUEST_CODE_PERMISSION = 0
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CODE_PERMISSION
                )
            }
        } else {
            detenerGrabacion()
        }
    }

    private val updateProgress = object : Runnable {
        override fun run() {
            // Actualiza el progreso de la barra cada segundo
            progressBar?.progress = recordingProgress

            if (recordingProgress < recordingDuration) {
                recordingProgress++
                handler.postDelayed(this, 1000)
            }

            if (recordingProgress == recordingDuration) {
                // Si se alcanza la duración máxima, detiene la grabación
                detenerGrabacion()
            }
        }
    }

    private fun detenerGrabacion() {
        try {
            grabadora?.stop()
            grabadora?.reset()
            grabadora?.release()
            grabadora = null
            imgGrabar?.setBackgroundColor(Color.BLACK)
            Toast.makeText(applicationContext, "Terminó de Grabar", Toast.LENGTH_SHORT).show()

            // Detiene el hilo de actualización de la barra de progreso
            handler.removeCallbacks(updateProgress)

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "Error al reproducir: IOException", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "Error al reproducir: IllegalStateException", Toast.LENGTH_SHORT).show()
        }

    }

    fun reproducir(view: View) {
        if (ruta != null) {
            try {
                if (mediaPlayer == null) {
                    // Si el MediaPlayer es nulo, crea una nueva instancia
                    mediaPlayer = MediaPlayer()
                    mediaPlayer?.setDataSource(ruta)
                    mediaPlayer?.prepare()
                } else {
                    // Si el MediaPlayer ya está creado, reinicialízalo
                    mediaPlayer?.reset()
                    mediaPlayer?.setDataSource(ruta)
                    mediaPlayer?.prepare()
                }

                mediaPlayer?.start()
                Toast.makeText(applicationContext, "Reproducción en curso", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Error al reproducir", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(applicationContext, "No hay archivo para reproducir", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000) {
            // Verifica si los permisos fueron concedidos
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Puedes iniciar la grabación aquí o notificar al usuario
            } else {
                // Puedes informar al usuario que los permisos son necesarios para la grabación
                Toast.makeText(this, "Se requieren permisos para la grabación de audio", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        // Detener la grabación y liberar recursos
        detenerGrabacion()
        mediaPlayer?.release()
    }
}
