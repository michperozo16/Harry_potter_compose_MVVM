package com.mich.simpleres

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mich.simpleres.ui.theme.SimpleResTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleResTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp1()
                }
            }
        }
    }
}

@Composable
fun MyApp1(
viewModel: UserViewModel = hiltViewModel()

) {
    MyApp(onAddClick = {
        viewModel.addUser()
})

}

@Composable
fun MyApp(
    onAddClick: (()->Unit)? = null,
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Simple Rest + Room") },
                actions = {
                    IconButton(onClick = {
                        onAddClick?.invoke()
                    }) {
                        Icon(Icons.Filled.Add, "Add" )

                    }
                }
            )
        }
    ) {}
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleResTheme {
        MyApp(onAddClick = null)
    }
}