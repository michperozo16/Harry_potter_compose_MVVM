package com.example.torredegracian

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val DURACION: Long = 3000

    // URLs
    private val URL_YOUTUBE = "https://www.youtube.com/channel/UCbcJ0ueasmIUbpsLGgKzKKA"
    private val URL_FACEBOOK = "https://www.facebook.com/groups/662093197932076"
    private val URL_ENCUENTROS = "https://iglesiatorredegracianavarra.com/encuentros-de-amistad/"
    private val URL_ALABANZAS = "https://multimedia.iglesiatorredegracianavarra.com/canciones"
    private val URL_MINISTROS = "https://iglesiatorredegracianavarra.com/zona_de_ministros/"
    private val URL_TORRE = "https://multimedia.iglesiatorredegracianavarra.com"
    private val URL_INSTAGRAM = "https://www.instagram.com/iglesia_torredegracianavarra/"
    private val URL_WEBPRINCIPAL = "https://iglesiatorredegracianavarra.com"
    private val URL_ESCUELA = "https://edu.iglesiatorredegracianavarra.com/login/index.php"
    private val URL_BARCELONA_WEB = "https://www.torredegraciabarcelona.com"
   // private val URL_ESCUELA_BCN = "https://www.cosechamundialbcn.es/outreach/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        setButtonClickListeners()

    }

    private fun setButtonClickListeners() {
        findViewById<Button>(R.id.btn_youtube).setOnClickListener { abrirURL(URL_YOUTUBE) }
        findViewById<Button>(R.id.btn_facebook).setOnClickListener { abrirURL(URL_FACEBOOK) }
        findViewById<Button>(R.id.btn_encuentros).setOnClickListener { abrirURL(URL_ENCUENTROS) }
        findViewById<Button>(R.id.btn_alabanzas).setOnClickListener { abrirURL(URL_ALABANZAS) }
        findViewById<Button>(R.id.btn_ministros).setOnClickListener { abrirURL(URL_MINISTROS) }
        findViewById<Button>(R.id.btn_webTorre).setOnClickListener { abrirURL(URL_TORRE) }
        findViewById<Button>(R.id.btn_instagram).setOnClickListener { abrirURL(URL_INSTAGRAM) }
        findViewById<Button>(R.id.btn_principal).setOnClickListener { abrirURL(URL_WEBPRINCIPAL) }
        findViewById<Button>(R.id.btn_escuela).setOnClickListener { abrirURL(URL_ESCUELA) }
        findViewById<Button>(R.id.btn_webTorreBCN).setOnClickListener { abrirURL(URL_BARCELONA_WEB) }
       // findViewById<Button>(R.id.btn_EscuelaBTN).setOnClickListener { abrirURL(URL_ESCUELA_BCN) }
    }

    private fun abrirURL(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            mostrarToast("No hay aplicación disponible para manejar la URL.")
            e.printStackTrace()
        } catch (e: Exception) {
            mostrarToast("Error al abrir la URL.")
            e.printStackTrace()
        }
    }
    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}