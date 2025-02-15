package com.example.eltiempo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eltiempo.data.ForecastResponse
import com.example.eltiempo.network.WeatherApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class WeatherViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherService = retrofit.create(WeatherApi::class.java)
    private val apiKey = "97f13fc32f7105c0970ac7ad563f8db9"

    private val _forecastState = MutableStateFlow<ForecastState>(ForecastState.Initial)
    val forecastState: StateFlow<ForecastState> = _forecastState

    init {
        getForecast("Tudela,ES")
    }

    fun getForecast(city: String) {
        viewModelScope.launch {
            try {
                _forecastState.value = ForecastState.Loading
                val response = weatherService.getForecast(city, apiKey)
                _forecastState.value = ForecastState.Success(response)
            } catch (e: Exception) {
                _forecastState.value = ForecastState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    sealed class ForecastState {
        object Initial : ForecastState()
        object Loading : ForecastState()
        data class Success(val forecast: ForecastResponse) : ForecastState()
        data class Error(val message: String) : ForecastState()
    }
} 