package com.mich.mvvmlogion.navegacion

 sealed class Rutas (var ruta: String) {
     object Home:Rutas("ruta_home")
     object Settings:Rutas("ruta_settings")
}