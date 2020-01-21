package io.kommons.designpatterns.event.aggregator

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class EventTest {

    @Test
    fun `event to string`() {
        Event.values().forEach { event ->
            event.toString() shouldEqual event.name.toLowerCase().replace("_", " ").capitalize()
        }
    }
}