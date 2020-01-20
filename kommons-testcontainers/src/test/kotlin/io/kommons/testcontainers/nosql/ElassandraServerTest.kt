package io.kommons.testcontainers.nosql

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test

/**
 * ElassandraServerTest
 *
 * @author debop
 */
class ElassandraServerTest {

    companion object: KLogging()

    @Test
    fun `launch elassandra server`() {
        ElassandraServer().apply { start() }.use { elassandra ->
            elassandra.isRunning.shouldBeTrue()
        }
    }

    @Test
    fun `launch elassandra server with default port`() {
        ElassandraServer(useDefaultPort = true).apply { start() }.use { elassandra ->
            elassandra.isRunning.shouldBeTrue()

            elassandra.elasticsearchPort shouldEqualTo ElassandraServer.ELASTICSEARCH_PORT
            elassandra.cqlPort shouldEqualTo ElassandraServer.CQL_PORT
        }
    }
}