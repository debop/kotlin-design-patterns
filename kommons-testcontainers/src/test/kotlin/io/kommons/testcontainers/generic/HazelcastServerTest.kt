package io.kommons.testcontainers.generic

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("현재로서는 Hazelcast를 사용하지 않는다.")
class HazelcastServerTest {

    companion object: KLogging()

    @Test
    fun `create hazelcast server`() {
        HazelcastServer()
            .withRESTClient()
            .withHttpHealthCheck()
            .use {
                it.start()
                assertInstance(it)
            }
    }

    @Test
    fun `create hazelcast server with default port`() {
        HazelcastServer(useDefaultPort = true)
            .withRESTClient()
            .withHttpHealthCheck()
            .use {
                it.start()
                assertInstance(it)
                it.port shouldEqualTo HazelcastServer.HAZELCAST_PORT
            }
    }

    private fun assertInstance(hazelcast: HazelcastServer) {
        hazelcast.shouldNotBeNull()
        hazelcast.isRunning.shouldBeTrue()
    }
}