package com.mich.mvvmlogion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mich.mvvmlogion.navegacion.Rutas
import com.mich.mvvmlogion.ui.login.ui.LoginScreen
import com.mich.mvvmlogion.ui.login.ui.LoginViewModel
import com.mich.mvvmlogion.ui.theme.MVVMLogionTheme

sealed class Screen(val ruta:String){
    object HOME: Screen("Home")
    object LOGIN: Screen("Login")
    object SETTING: Screen("Setting")
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMLogionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //LoginScreen(LoginViewModel())
                    //estudiar como pasar de login a pantalla
                    NavigatinGraph()
                }
            }
        }
    }
}

@Preview
@Composable
fun NavigatinGraph() {
    val navController = rememberNavController( )
  NavHost(navController = navController, startDestination = Rutas.Home.ruta){
     composable(route = Rutas.Home.ruta){
         HomeScreen(navController)
     }

      composable(route = Rutas.Settings.ruta){
          SettingsScreen(navController)
      }
  }
}

