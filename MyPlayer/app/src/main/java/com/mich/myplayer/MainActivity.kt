package com.mich.myplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val message: EditText = findViewById(R.id.message)
        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
          toast("Hola ${message.text}")
        }
    }
        private fun toast(message: String) {
            Toast.makeText(this,message, Toast.LENGTH_SHORT).show()


    }
}