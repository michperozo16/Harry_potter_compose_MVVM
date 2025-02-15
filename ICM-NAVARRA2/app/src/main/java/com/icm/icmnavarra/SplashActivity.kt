package com.icm.icmnavarra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//si tengo que hacer loguin lo tengo que hacer aqui.

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))




    }
}