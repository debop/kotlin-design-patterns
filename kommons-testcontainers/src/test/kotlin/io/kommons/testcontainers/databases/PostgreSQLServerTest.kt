package io.kommons.testcontainers.databases

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.testcontainers.containers.PostgreSQLContainer

@Disabled("현재로서는 PostgreSQLServer를 사용하지 않는다.")
class PostgreSQLServerTest {

    companion object: KLogging()

    @Test
    fun `create postgresql server`() {
        PostgreSQLServer().use { postgres ->
            postgres.start()
            assertConnection(postgres)
        }
    }

    @Test
    fun `create postgresql server with default port`() {
        PostgreSQLServer(useDefaultPort = true).use { postgres ->
            postgres.start()
            postgres.port shouldEqualTo PostgreSQLContainer.POSTGRESQL_PORT
            assertConnection(postgres)
        }
    }

    private fun assertConnection(postgres: PostgreSQLServer) {
        postgres.getDataSource().use { ds ->
            ds.connection.use { conn ->
                conn.isValid(1).shouldBeTrue()
            }
        }
    }
}