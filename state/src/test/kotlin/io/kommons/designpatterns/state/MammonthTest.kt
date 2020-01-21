package io.kommons.designpatterns.state

import io.kommons.junit.jupiter.output.InMemoryAppender
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MammonthTest {

    companion object: KLogging()

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setup() {
        appender = InMemoryAppender()
    }

    @AfterEach
    fun cleanup() {
        appender.stop()
    }

    @Test
    fun `run timePasses`() {
        val mammoth = Mammoth()

        mammoth.observe()
        appender.lastMessage shouldEqual "The mammoth is calm and peaceful."
        appender.size shouldEqualTo 1

        mammoth.timepasses()
        appender.lastMessage shouldEqual "The mammoth gets angry!"
        appender.size shouldEqualTo 2

        mammoth.observe()
        appender.lastMessage shouldEqual "The mammoth is furious!"
        appender.size shouldEqualTo 3

        mammoth.timepasses()
        appender.lastMessage shouldEqual "The mammoth calms down."
        appender.size shouldEqualTo 4

        mammoth.observe()
        appender.lastMessage shouldEqual "The mammoth is calm and peaceful."
        appender.size shouldEqualTo 5
    }

    @Test
    fun `verify toString`() {
        val toString = Mammoth().toString()
        toString shouldBeEqualTo "The mammoth"
    }
}