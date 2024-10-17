import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.weatherRoutes() {
    routing {
        get("/weather/{location}") {
            val location = call.parameters["location"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val weather = WeatherService.getWeather(location)
            if (weather != null) {
                call.respondText(weather, ContentType.Application.Json)
            } else {
                call.respond(HttpStatusCode.NotFound, "No data available for $location")
            }
        }
    }
}
