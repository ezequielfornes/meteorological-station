import redis.clients.jedis.JedisPool

object RedisClient {
    private val jedis = JedisPool("localhost", 6379)

    fun get(key: String): String? {
        jedis.resource.use { return it.get(key) }
    }

    fun set(key: String, value: String) {
        jedis.resource.use { it.set(key, value) }
    }

    fun logError(error: String) {
        val timestamp = System.currentTimeMillis()
        jedis.resource.use { it.set("error_log_$timestamp", error) }
    }
}
