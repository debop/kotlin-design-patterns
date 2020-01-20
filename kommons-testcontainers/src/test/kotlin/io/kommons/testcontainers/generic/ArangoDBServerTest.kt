package io.kommons.testcontainers.generic

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Test

class ArangoDBServerTest {

    companion object: KLogging()

    @Test
    fun `launch arangodb server`() {
        ArangoDBServer()
            .apply { start() }
            .use {
                it.isRunning.shouldBeTrue()
            }
    }
}