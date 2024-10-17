import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Scheduler {

    fun start() {
        GlobalScope.launch {
            while (true) {
                val locations = listOf("Santiago de chile", "Zurich", "Auckland", "Sydney", "London", "Georgia")
                locations.forEach { location ->
                    try {
                        val weather = WeatherService.fetchWeather(location)
                        RedisClient.set(location, weather)
                    } catch (e: Exception) {
                        RedisClient.logError("${e.message} - $location")
                    }
                }
                delay(5 * 60 * 1000L) // Espera de 5 minutos
            }
        }
    }
}
