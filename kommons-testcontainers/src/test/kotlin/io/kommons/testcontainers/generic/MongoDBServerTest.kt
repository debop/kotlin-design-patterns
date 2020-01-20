package io.kommons.testcontainers.generic

import com.mongodb.MongoClient
import io.kommons.logging.KLogging
import io.kommons.logging.debug
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.bson.Document
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class MongoDBServerTest {

    companion object: KLogging()

    private lateinit var mongodb: MongoDBServer

    @BeforeAll
    fun beforeAll() {
        mongodb = MongoDBServer().apply { start() }
    }

    @AfterAll
    fun afterAll() {
        if (this::mongodb.isInitialized) {
            mongodb.close()
        }
    }

    @Test
    fun `create mongodb testcontainer instance`() {
        mongodb.shouldNotBeNull()
        log.debug { "MongoDB host=${mongodb.host}, port=${mongodb.port}" }
    }

    @Test
    fun `connect to mongodb`() {
        val client = MongoClient(mongodb.host, mongodb.port)

        client.listDatabaseNames().forEach {
            log.debug { "Database name=$it" }
        }

        val db = client.getDatabase("local")
        db.listCollectionNames().forEach {
            log.debug { "  Collection=$it" }
        }
    }

    @Test
    fun `save and read with customer collection`() {
        val client = MongoClient(mongodb.host, mongodb.port)

        val db = client.getDatabase("local")

        db.createCollection("customers")
        val customers = db.getCollection("customers")

        val document = Document().apply {
            put("name", "Debop")
            put("company", "Coupang")
        }
        customers.insertOne(document)

        val loaded = customers.find().toList()
        loaded.size shouldEqualTo 1
    }

    @Test
    fun `launch use default port`() {
        MongoDBServer(useDefaultPort = true)
            .apply { start() }
            .use { mongo ->
                mongo.isRunning.shouldBeTrue()
            }
    }
}