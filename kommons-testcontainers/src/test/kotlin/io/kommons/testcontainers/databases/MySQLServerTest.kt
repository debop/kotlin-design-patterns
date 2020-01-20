package io.kommons.testcontainers.databases

import io.kommons.logging.KLogging
import io.kommons.testcontainers.databases.MySQLServer.Companion.MYSQL_8_TAG
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test
import org.testcontainers.containers.MySQLContainer

class MySQLServerTest {

    companion object: KLogging()

    @Test
    fun `create maraiadb server`() {
        MySQLServer().use { mysql ->
            mysql.start()
            assertConnection(mysql)
        }
    }

    @Test
    fun `create mysql server with default port`() {
        MySQLServer(useDefaultPort = true).use { mysql ->
            mysql.start()
            mysql.port shouldEqualTo MySQLContainer.MYSQL_PORT
            assertConnection(mysql)
        }
    }

    @Test
    fun `create mysql server 8+`() {
        // HINT: MySQL 8+ 에서는 username="root", password="" 이어야 제대로 실행됩니다.
        // HINT: 아마 보안 강화로 SSL 관련된 설정을 추가해야 할 것으로 추측합니다.

        MySQLServer(tag = MYSQL_8_TAG, username = "root", password = "").use { mysql ->
            mysql.start()
            assertConnection(mysql)
        }
    }

    private fun assertConnection(mysql: MySQLServer) {
        mysql.getDataSource().use { ds ->
            ds.connection.use { conn ->
                conn.isValid(1).shouldBeTrue()
            }
        }
    }
}