package io.kommons.junit.jupiter.output

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import io.kommons.logging.info
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest

class InMemoryAppenderTest {

    companion object: KLogging()

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun beforeEach() {
        appender = InMemoryAppender(InMemoryAppenderTest::class)
    }

    @AfterEach
    fun afterEach() {
        appender.stop()
    }

    @RepeatedTest(5)
    fun `capture logback log message`() {
        log.debug { "First message" }
        appender.lastMessage shouldEqual "First message"
        appender.size shouldEqualTo 1

        log.debug { "Second message" }
        appender.lastMessage shouldEqual "Second message"
        appender.size shouldEqualTo 2

        appender.clear()
        appender.size shouldEqualTo 0
        appender.lastMessage.shouldBeNull()
        appender.messages.shouldBeEmpty()
    }

    @RepeatedTest(5)
    fun `capture logback log message with info level`() {
        appender.messages.isEmpty().shouldBeTrue()
        log.info { "Information" }
        appender.messages.size shouldEqualTo 1
        appender.lastMessage shouldEqual "Information"
    }
}