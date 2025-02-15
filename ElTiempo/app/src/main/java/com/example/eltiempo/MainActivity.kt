package com.example.eltiempo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eltiempo.data.ForecastItem
import com.example.eltiempo.ui.theme.ElTiempoTheme
import com.example.eltiempo.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElTiempoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ForecastScreen()
                }
            }
        }
    }
}

@Composable
fun ForecastScreen(viewModel: WeatherViewModel = viewModel()) {
    val forecastState = viewModel.forecastState.collectAsState()
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1565C0),  // Azul más intenso
            Color(0xFF2196F3)   // Azul más brillante
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.weather_logo),
                contentDescription = "Logo del tiempo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "Pronóstico del Tiempo",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            when (val state = forecastState.value) {
                is WeatherViewModel.ForecastState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.White
                    )
                }
                is WeatherViewModel.ForecastState.Success -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(state.forecast.list.filterIndexed { index, _ ->
                            index % 8 == 0
                        }.take(7)) { forecastItem ->
                            WeatherCard(forecastItem)
                        }
                    }
                }
                is WeatherViewModel.ForecastState.Error -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFEBEE)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Error",
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Error: ${state.message}",
                                color = Color.Red
                            )
                        }
                    }
                }
                is WeatherViewModel.ForecastState.Initial -> {
                    Text(
                        text = "Cargando pronóstico...",
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherCard(forecastItem: ForecastItem) {
    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val isToday = forecastItem.dt_txt.split(" ")[0] == today

    val cardColor = if (isToday) {
        Color(0xFF1565C0) // Azul intenso para el día actual
    } else {
        when (forecastItem.weather.first().id) {
            in 200..232 -> Color(0xFF5C6BC0) // Tormenta
            in 300..321 -> Color(0xFF42A5F5) // Llovizna
            in 500..531 -> Color(0xFF1E88E5) // Lluvia
            in 600..622 -> Color(0xFF90CAF9) // Nieve
            in 701..781 -> Color(0xFF78909C) // Niebla
            800 -> Color(0xFF2196F3)         // Despejado
            in 801..802 -> Color(0xFF64B5F6) // Parcialmente nublado
            in 803..804 -> Color(0xFF4FC3F7) // Nublado
            else -> Color(0xFF2196F3)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatDate(forecastItem.dt_txt.split(" ")[0]),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = "${formatTemperature(forecastItem.main.temp)}°C",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = getWeatherEmoji(forecastItem.weather.first().id),
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = forecastItem.weather.first().description.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    },
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherDetail(
                    emoji = "🌡️",
                    label = "Sensación",
                    value = "${formatTemperature(forecastItem.main.feels_like)}°C"
                )

                WeatherDetail(
                    emoji = "💧",
                    label = "Humedad",
                    value = "${forecastItem.main.humidity}%"
                )

                WeatherDetail(
                    emoji = "💨",
                    label = "Viento",
                    value = "${String.format("%.1f", forecastItem.wind.speed)} m/s"
                )

                WeatherDetail(
                    emoji = "☔",
                    label = "Lluvia",
                    value = "${String.format("%.0f", forecastItem.pop * 100)}%"
                )
            }
        }
    }
}

@Composable
private fun WeatherDetail(
    emoji: String,
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = emoji,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.White.copy(alpha = 0.9f)
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

private fun getWeatherEmoji(id: Int): String = when (id) {
    in 200..232 -> "⛈️"  // Tormenta
    in 300..321 -> "🌧️"  // Llovizna
    in 500..531 -> "🌧️"  // Lluvia
    in 600..622 -> "🌨️"  // Nieve
    in 701..781 -> "🌫️"  // Niebla/Atmósfera
    800 -> "☀️"          // Despejado
    in 801..802 -> "🌤️"  // Parcialmente nublado
    in 803..804 -> "☁️"  // Nublado
    else -> "❓"
}

private fun formatDate(dateStr: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("EEEE d MMM", Locale("es", "ES"))
    val date = inputFormat.parse(dateStr)
    return outputFormat.format(date!!).replaceFirstChar { it.uppercase() }
}

private fun formatTemperature(temp: Double): String {
    return temp.toInt().toString()
}