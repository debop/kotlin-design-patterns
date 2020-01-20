package io.kommons.testcontainers.generic

import io.kommons.logging.KLogging
import io.kommons.logging.info
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeNull
import org.apache.ignite.Ignite
import org.apache.ignite.Ignition
import org.apache.ignite.configuration.IgniteConfiguration
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class IgniteServerTest {

    companion object: KLogging()

    lateinit var ignite: IgniteServer

    @BeforeAll
    fun setup() {
        ignite = IgniteServer(useDefaultPort = true).apply { start() }
    }

    @AfterAll
    fun cleanup() {
        ignite.close()
    }

    @Test
    fun `create ignite server`() {
        ignite.isCreated.shouldBeTrue()
        ignite.isRunning.shouldBeTrue()
        ignite.port shouldEqual 11211
    }

    @Test
    fun `put and get data with ignite server`() {
        usingIgnite {
            this.shouldNotBeNull()

            getOrCreateCache<String, String?>("test").use { cache ->
                cache.put("key", "value")
                cache["key"] shouldEqual "value"
            }
        }
    }

    inline fun <T> usingIgnite(cfg: IgniteConfiguration = IgniteConfiguration(), action: Ignite.() -> T): T {
        return startIgnite(cfg).use {
            action(it)
        }
    }

    fun startIgnite(cfg: IgniteConfiguration = IgniteConfiguration()): Ignite {
        log.info { "Start or get Ignite container... cfg=$cfg" }
        return Ignition.getOrStart(cfg)
    }
}