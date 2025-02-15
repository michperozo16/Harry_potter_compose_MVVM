package com.mich.mispracticascompose

import android.content.res.Configuration
import android.graphics.Paint.Style
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mich.mispracticascompose.ui.theme.MisPracticasComposeTheme
import com.mich.mispracticascompose.ui.theme.White
import org.w3c.dom.Text
import java.lang.reflect.Modifier
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MisPracticasComposeTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var name by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Pedro")
        TextField(value = name, onValueChange = {
            name = it
        })
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Presioname")
        }
    }
}

@Preview()
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewComponent() {
    MisPracticasComposeTheme {
       MainScreen()
    }
}
