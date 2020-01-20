package io.kommons.logging

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test
import java.time.Instant


private val packageLogger = KotlinLogging.logger {}

class KLoggingTest {

    companion object: KLogging()

    private val error = RuntimeException("Boom!")

    @Test
    fun `logging trace`() {
        log.trace { "Message at ${Instant.now()}" }
        log.trace(error) { "Error at ${Instant.now()}" }
    }

    @Test
    fun `logging debug`() {
        log.debug { "Message at ${Instant.now()}" }
        log.debug(error) { "Error at ${Instant.now()}" }
    }

    @Test
    fun `logging info`() {
        log.info { "Message at ${Instant.now()}" }
        log.info(error) { "Error at ${Instant.now()}" }
    }

    @Test
    fun `logging warn`() {
        log.warn { "Message at ${Instant.now()}" }
        log.warn(error) { "Error at ${Instant.now()}" }
    }

    @Test
    fun `logging error`() {
        log.error { "Message at ${Instant.now()}" }
        log.error(error) { "Error at ${Instant.now()}" }
    }

    @Test
    fun `logging trace by packageLogger`() {
        packageLogger.trace { "Message at ${Instant.now()}" }
        packageLogger.trace(error) { "Error at ${Instant.now()}" }
    }

    @Test
    fun `logger by name`() {
        val logger = KotlinLogging.logger("Foo")
        logger.name shouldEqual "Foo"
        logger.info { "logger is Foo" }
        logger.warn(error) { "logger is Foo" }
    }
}