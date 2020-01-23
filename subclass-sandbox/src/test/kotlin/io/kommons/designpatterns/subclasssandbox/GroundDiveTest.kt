package io.kommons.designpatterns.subclasssandbox

import io.kommons.junit.jupiter.output.InMemoryAppender
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldHaveSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GroundDiveTest {

    companion object: KLogging()

    private lateinit var appender: InMemoryAppender

    private val groundDive = GroundDive()

    @BeforeEach
    fun setup() {
        appender = InMemoryAppender()
    }

    @AfterEach
    fun cleanup() {
        appender.stop()
    }

    @Test
    fun `test move`() {
        groundDive.move(1.0, 1.0, 1.0)
        appender.lastMessage shouldEqual "Move to (1.0, 1.0, 1.0)"
    }

    @Test
    fun `play sound`() {
        groundDive.playSound("SOUND_NAME", 1)
        appender.lastMessage shouldEqual "Play SOUND_NAME with volume 1"
    }

    @Test
    fun `activate groundDive`() {
        groundDive.activate()

        appender.messages shouldHaveSize 3

        appender.messages[0] shouldEqual "Move to (0.0, 0.0, -20.0)"
        appender.messages[1] shouldEqual "Play GROUNDDIVE_SOUND with volume 5"
        appender.messages[2] shouldEqual "Spawn 20 particles with type GROUNDDIVE_PARTICLE"
    }
}