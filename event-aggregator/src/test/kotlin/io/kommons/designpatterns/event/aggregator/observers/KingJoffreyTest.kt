package io.kommons.designpatterns.event.aggregator.observers

import io.kommons.designpatterns.event.aggregator.Event
import io.kommons.junit.jupiter.output.InMemoryAppender
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KingJoffreyTest {

    private var appender = InMemoryAppender()

    @BeforeEach
    fun setup() {
        appender = InMemoryAppender()
    }

    @AfterEach
    fun cleanup() {
        appender.stop()
    }

    @Test
    fun `onEvent called`() {
        val kingJoffrey = KingJoffrey()

        Event.values().forEachIndexed { index, event ->
            appender.size shouldEqualTo index
            kingJoffrey.onEvent(event)

            val expectedMessage = "Received event from the King's Hand: $event"
            appender.lastMessage shouldEqual expectedMessage
            appender.size shouldEqualTo index + 1
        }
    }
}