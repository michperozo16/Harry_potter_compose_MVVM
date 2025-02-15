package com.mich.mvvmlogion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.mich.mvvmlogion.navegacion.Rutas


@Composable
fun HomeScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()){
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                navController.navigate(route = Rutas.Settings.ruta)
            })
        {
            Text(text = "Vamos a Settings")
        }
    }
}



@Composable
fun SettingsScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize().background(color = Color.Cyan)){
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                navController.popBackStack()
            })
        {
            Text(text = "Vamos a Home")
        }
    }
}