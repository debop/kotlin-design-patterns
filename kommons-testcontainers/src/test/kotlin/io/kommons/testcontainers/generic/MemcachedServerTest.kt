package io.kommons.testcontainers.generic

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("Not used")
class MemcachedServerTest {

    companion object: KLogging()

    private lateinit var memcached: MemcachedServer

    @BeforeAll
    fun setup() {
        memcached = MemcachedServer()
        memcached.start()
    }

    @AfterAll
    fun cleanup() {
        memcached.close()
    }

    @Test
    fun `create memcached server`() {
        memcached.isRunning.shouldBeTrue()
        memcached.host shouldEqual "localhost"
    }

    @Test
    fun `connect to memcached server`() {
        val client = newFolsomClient(memcached.host, memcached.port)
        client.shouldNotBeNull()
        client.isConnected.shouldBeTrue()
        client.shutdown()
    }

    // @Disabled("테스트 완료")
    @Test
    fun `create memcached server with default port`() {
        val memcached = MemcachedServer(useDefaultPort = true)
        memcached.start()
        memcached.isRunning.shouldBeTrue()
        memcached.port shouldEqualTo MemcachedServer.MEMCACHED_PORT

        val client = newFolsomClient(memcached.host, memcached.port)
        client.shouldNotBeNull()
        client.isConnected.shouldBeTrue()
        client.shutdown()

        memcached.close()
    }

    @Test
    fun `multiple connection and shutdown`() = runBlocking<Unit> {
        val jobs = (1..20).map {
            launch(Dispatchers.Default) {
                log.debug { "connect ... $it" }
                val client = newFolsomClient(memcached.host, memcached.port)

                client.isConnected.shouldBeTrue()

                delay(20)
                log.debug {
                    "total connections=${client.numTotalConnections()}, active connections=${client.numActiveConnections()}"
                }
                client.shutdown()
            }
        }
        jobs.joinAll()
    }

    @Test
    fun `put and get data`() {
        val client = newFolsomClient(memcached.host, memcached.port)

        val key = "key"
        val value = "value"

        client.set(key, value.toByteArray(), 3600).toCompletableFuture().join()

        val cached = client.get(key).toCompletableFuture().join()?.toString(Charsets.UTF_8)
        cached shouldEqual value
    }
}