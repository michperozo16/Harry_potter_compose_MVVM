import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/onecall")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double = 42.0605,  // Latitud de Tudela
        @Query("lon") lon: Double = -1.6056,  // Longitud de Tudela
        @Query("exclude") exclude: String = "current,minutely,hourly,alerts",
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = "97f13fc32f7105c0970ac7ad563f8db9"
    ): WeatherResponse
} 