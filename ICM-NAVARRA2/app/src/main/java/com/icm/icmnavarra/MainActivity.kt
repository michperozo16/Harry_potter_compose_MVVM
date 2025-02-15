package com.icm.icmnavarra

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // val btn_anterior=findViewById<Button>(R.id.btn_anterior)


        val btn_Pastor = findViewById<Button>(R.id.btn_Pastor)
        val btn_Pastora = findViewById<Button>(R.id.btn_Pastora)
        val btn_Ministros = findViewById<Button>(R.id.btn_Ministros)
        val btn_Discipulado = findViewById<Button>(R.id.btn_Discipulado)
        val btn_Facebook = findViewById<Button>(R.id.btn_Facebook)
        val btn_Alavanzas = findViewById<Button>(R.id.btn_Alavanzas)
        val btn_Video = findViewById<Button>(R.id.btn_video)
        val btn_Diario = findViewById<Button>(R.id.btn_Diario)
        val btn_Biblia = findViewById<Button>(R.id.btn_biblia)
        val btn_Instagram = findViewById<Button>(R.id.btn_Istagram)
        val btn_eneagrama = findViewById<Button>(R.id.btn_eneagrama)
        val btn_home = findViewById<Button>(R.id.btn_home)

        btn_Video.setOnClickListener {
            val miIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://cosechamundialnavarra.com/multimedia/")
            )
            startActivity(miIntent)
        }

        btn_Facebook.setOnClickListener {
            val miIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/groups/662093197932076")
            )
            startActivity(miIntent)
        }
        btn_Diario.setOnClickListener {
            val miIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/groups/236611451987843")
            )
            startActivity(miIntent)
        }

        btn_Biblia.setOnClickListener {
            val miIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.iglesia.net/biblia/"))
            startActivity(miIntent)
        }

        btn_Pastor.setOnClickListener {
            val miIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://cosechamundialnavarra.com/videos/"))
            startActivity(miIntent)
        }

        btn_Pastora.setOnClickListener {
            val miIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://cosechamundialnavarra.com/videos-pastora/")
            )
            startActivity(miIntent)
        }

        btn_Alavanzas.setOnClickListener {
            val miIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://cosechamundialnavarra.com/alabanzas-y-adoracion/")
            )
            startActivity(miIntent)
        }

        btn_Ministros.setOnClickListener {
            val miIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://cosechamundialnavarra.com/ministros-icm/")
            )
            startActivity(miIntent)
        }

        btn_Discipulado.setOnClickListener {
            val miIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://edu.cosechamundialnavarra.com/login/index.php")
            )
            startActivity(miIntent)
        }
        btn_Instagram.setOnClickListener {
            val miIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.instagram.com/cosechamundialnavarra/")
            )
            startActivity(miIntent)
        }
        btn_eneagrama.setOnClickListener {
            val miIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://cosechamundialnavarra.com/eneagrama-2022/")
            )
            startActivity(miIntent)
        }
        btn_home.setOnClickListener {
            val miIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://cosechamundialnavarra.com/")
            )
            startActivity(miIntent)
        }
    }
}
