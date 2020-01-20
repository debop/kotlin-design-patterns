package io.kommons.testcontainers.generic

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class RedisServerTest {

    companion object: KLogging()

    private lateinit var redisServer: RedisServer

    @BeforeAll
    fun setup() {
        redisServer = RedisServer().apply { start() }
    }

    @AfterAll
    fun cleanup() {
        redisServer.close()
    }

    @Test
    fun `create redis server`() {
        redisServer.isRunning.shouldBeTrue()
        getAndSetWithRedisServer(redisServer)
    }

    @Test
    fun `create redis server with default port`() {
        RedisServer(useDefaultPort = true).use { redisServer ->
            redisServer.start()
            redisServer.isRunning.shouldBeTrue()
            redisServer.port shouldEqualTo RedisServer.REDIS_PORT
            getAndSetWithRedisServer(redisServer)
        }
    }

    private fun getAndSetWithRedisServer(redisServer: RedisServer) {
        val redisson = redissonClient(redisServer.url)

        val map = redisson.getMap<String, String>("map")
        map.fastPut("key1", "value1")

        val map2 = redisson.getMap<String, String>("map")
        map2["key1"] shouldEqual "value1"
    }
}