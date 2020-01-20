package io.kommons.testcontainers.nosql

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test

/**
 * ElasticsearchServerTest
 *
 * @author debop
 */
class ElasticsearchServerTest {

    companion object: KLogging()

    @Test
    fun `launching elasticsearch oss version`() {
        ElasticsearchServer()
            .apply { start() }
            .use { server ->
                server.isRunning.shouldBeTrue()
            }
    }

    @Test
    fun `launching elasticsearch oss version use default port`() {
        ElasticsearchServer(useDefaultPort = true)
            .apply { start() }
            .use { server ->
                server.isRunning.shouldBeTrue()
                server.port shouldEqualTo ElasticsearchServer.DEFAULT_PORT
            }
    }
}