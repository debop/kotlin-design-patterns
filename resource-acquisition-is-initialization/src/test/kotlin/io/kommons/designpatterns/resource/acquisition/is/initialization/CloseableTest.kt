package io.kommons.designpatterns.resource.acquisition.`is`.initialization

import io.kommons.junit.jupiter.output.InMemoryAppender
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CloseableTest {

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
    fun `open and close`() {
        SlidingDoor().use { door ->
            appender.lastMessage shouldEqual "Sliding door opens."

            TreasureChest().use { chest ->
                appender.lastMessage shouldEqual "Treasure chest opens."
            }

            appender.lastMessage shouldEqual "Treasure chest closes."
        }
        appender.lastMessage shouldEqual "Sliding door closes."
    }

}