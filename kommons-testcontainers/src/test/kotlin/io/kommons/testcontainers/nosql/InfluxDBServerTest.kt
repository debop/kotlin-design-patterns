package io.kommons.testcontainers.nosql

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.influxdb.dto.Point
import org.influxdb.dto.Query
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

class InfluxDBServerTest {

    companion object: KLogging() {
        const val DATABASE = "test"
        const val USER = "test-user"
        const val PASSWORD = "test-password"

        val influxDBServer = InfluxDBServer().apply {
            withDatabase(DATABASE)
            withUsername(USER)
            withPassword(PASSWORD)
            start()
        }
    }

    @Test
    fun `create new database`() {
        val influxDB = influxDBServer.newInfluxDB

        influxDB.shouldNotBeNull()
        influxDB.ping().isGood.shouldBeTrue()
    }

    @Test
    fun `describe database`() {
        val actual = influxDBServer.newInfluxDB

        actual.shouldNotBeNull()
        actual.describeDatabases() shouldContain DATABASE
    }

    @Test
    fun `query for write and read`() {
        val influxDB = influxDBServer.newInfluxDB

        val point = Point.measurement("cpu")
            .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .addField("idle", 90L)
            .addField("user", 9L)
            .addField("system", 1L)
            .build()

        influxDB.write(point)

        val query = Query("SELECT idle FROM cpu", DATABASE)
        val result = influxDB.query(query)

        result.shouldNotBeNull()
        result.error.shouldBeNull()
        result.results.shouldNotBeNull()
        result.results.size shouldEqualTo 1
    }
}