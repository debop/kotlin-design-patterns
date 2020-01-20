package io.kommons.testcontainers.databases

import io.kommons.logging.KLogging
import io.kommons.logging.trace
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test
import javax.sql.DataSource

class DatabaseFactoryTest {

    companion object: KLogging()

    @Test
    fun `create mysql server`() {
        DatabaseFactory.newMySQLServer().use { mysql ->
            mysql.shouldNotBeNull()

            mysql.getDataSource().use { dataSource ->
                verifyConnectToServer(dataSource)
            }
        }
    }

    @Test
    fun `create mariadb server`() {
        DatabaseFactory.newMariaDBServer().use { mariadb ->
            mariadb.shouldNotBeNull()

            mariadb.getDataSource().use { dataSource ->
                verifyConnectToServer(dataSource)
            }
        }
    }

    @Test
    fun `create postgresql server`() {
        DatabaseFactory.newPostgreSQLServer().use { postgres ->
            postgres.shouldNotBeNull()

            postgres.getDataSource().use { dataSource ->
                verifyConnectToServer(dataSource)
            }
        }
    }

    private fun verifyConnectToServer(dataSource: DataSource, sql: String = "SELECT 1") {
        dataSource.connection.use { conn ->
            conn.shouldNotBeNull()

            conn.createStatement().use { stmt ->
                log.trace { "Before execute sql statements" }
                stmt.execute(sql).shouldBeTrue()
                log.trace { "Success execute sql statements" }
                Thread.sleep(100)
            }
            Thread.sleep(10)
        }
    }
}