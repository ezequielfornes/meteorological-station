import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

object WeatherService {

    suspend fun fetchWeather(location: String): String {
        if (Math.random() < 0.2) throw Exception("The API Request Failed")

        val apiKey = "tu-api-key"
        val url = "https://api.weatherapi.com/v1/current.json?key=$apiKey&q=$location"
        return HttpClient(CIO).use { client ->
            client.get(url).bodyAsText()
        }
    }


    suspend fun getWeather(location: String): String? {
        val cachedWeather = RedisClient.get(location)
        if (cachedWeather != null) return cachedWeather

        return try {
            val weather = fetchWeather(location)
            RedisClient.set(location, weather)
            weather
        } catch (e: Exception) {
            RedisClient.logError("${e.message} at ${System.currentTimeMillis()}")
            null
        }
    }
}
