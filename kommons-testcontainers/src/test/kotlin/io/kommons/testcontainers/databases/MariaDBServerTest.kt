package io.kommons.testcontainers.databases

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test

class MariaDBServerTest {

    companion object: KLogging()

    @Test
    fun `connect to mariadb server`() {
        MariaDBServer().use { mariadb ->
            mariadb.start()
            assertConnection(mariadb)
        }
    }

    @Test
    fun `create mariadb server with default port`() {
        MariaDBServer(useDefaultPort = true).use { mariadb ->
            mariadb.start()
            mariadb.port shouldEqualTo MariaDBServer.MARIADB_PORT

            assertConnection(mariadb)
        }
    }

    private fun assertConnection(mariadb: MariaDBServer) {
        mariadb.getDataSource().use { ds ->
            ds.connection.use { conn ->
                conn.isValid(1).shouldBeTrue()
            }
        }
    }
}