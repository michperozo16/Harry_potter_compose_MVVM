package com.mich.miappdeprueva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mich.miappdeprueva.ui.theme.MiAppDePruevaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiAppDePruevaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                  ComposePreview()

                }
            }
        }
    }
}
@Preview(showBackground = true, name = "Android Greeting")
@Composable
fun ComposePreview() {
    MiAppDePruevaTheme {
            Box{
                Text( modifier = Modifier.fillMaxWidth(),
                    text = "Bendecido",
                    color = Color.Red,
                    fontSize = 25.sp
                )
            }
        }
    }
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!")

}

