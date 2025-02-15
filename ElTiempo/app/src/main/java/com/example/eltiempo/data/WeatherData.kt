data class WeatherResponse(
    val daily: List<DailyWeather>
)

data class DailyWeather(
    val dt: Long,
    val temp: Temperature,
    val humidity: Int,
    val wind_speed: Double,
    val weather: List<Weather>,
    val pop: Double // Probabilidad de precipitación
)

data class Temperature(
    val day: Double,
    val min: Double,
    val max: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
) 