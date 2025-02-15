package com.mich.mymovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.MediaItem
import com.MediaItem.*
import com.getMedia
import com.mich.mymovies.ui.theme.MyMoviesTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMoviesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    var text by rememberSaveable { mutableStateOf("") } //para la rotacion remember Saveable es la solucion.
                    StateSample()
                }
            }
        }
    }

    private fun StateSample() {

    }
}
@Preview(showBackground = true, widthDp = 400, heightDp = 400)
@Composable
fun StateSample(value: String, onValueChange: (String) -> Unit){

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(64.dp),
        verticalArrangement = Arrangement.Center
    ){
        TextField(value = value,
            onValueChange = { onValueChange (it) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
                .padding(8.dp)
        )
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { text = "" },
            modifier = Modifier.fillMaxWidth(),
            enabled = text.isNotEmpty()
        ) {
            Text(text = "Borrar")
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalFoundationApi
@Preview
@Composable
fun MediaList() {
    LazyVerticalGrid (
        contentPadding = PaddingValues(2.dp),
        cells = GridCells.Adaptive(150.dp)

            ) {
        items (getMedia()) { item ->
            MediaListItem(item, Modifier.padding(2.dp))
        }
    }
}

@Preview(showBackground = true)
@ExperimentalFoundationApi
@Composable
fun MediaListItem(item: MediaItem, modifier: Modifier = Modifier) {
    Column (
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)

        ) {
       Image(
           painter = rememberImagePainter(
               data = item.thumb
           ),
           contentDescription = null,
           modifier = Modifier.fillMaxSize(),
           contentScale = ContentScale.Crop
          )
            if (item.type == Type.VIDEO) {
                Icon(
                    Icons.Default.PlayCircleOutline,
                    contentDescription = null,
                    modifier = Modifier
                        .size(92.dp)
                        .align(Alignment.Center),
                    tint = Color.Black
                )
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.secondary)
                .padding(16.dp)

        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.h6
            )
        }
    }
}
@Composable
fun Greeting(name: String,
             modifier: Modifier = Modifier) {
    Text(text = "Hello $name!",
        modifier = modifier
    )
}

//@Preview (showBackground = true, widthDp = 400, heightDp = 200)
@Composable
fun ButtonText() {
    Box(modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center){
        Text(
            text = "Provemos cosas",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h4.copy(
                shadow = Shadow(
                    offset = Offset(12f, 8f),
                    blurRadius = 5f,
                    color = Color.Black.copy(alpha = 0.5f)
                )
            )
        )
    }
}

