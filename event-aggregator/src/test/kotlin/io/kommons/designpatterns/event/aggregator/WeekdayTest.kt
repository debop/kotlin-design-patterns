package io.kommons.designpatterns.event.aggregator

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

/**
 * WeekdayTest
 *
 * @author debop
 */
class WeekdayTest {

    @Test
    fun `weekday toString`() {
        Weekday.values().forEach { weekday ->
            weekday.toString().toUpperCase() shouldEqual weekday.name
        }
    }
}