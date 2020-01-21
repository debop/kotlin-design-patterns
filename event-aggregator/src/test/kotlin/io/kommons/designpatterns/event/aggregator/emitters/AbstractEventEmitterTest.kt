package io.kommons.designpatterns.event.aggregator.emitters

import io.kommons.designpatterns.event.aggregator.Event
import io.kommons.designpatterns.event.aggregator.EventEmitter
import io.kommons.designpatterns.event.aggregator.EventObserver
import io.kommons.designpatterns.event.aggregator.Weekday
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

/**
 * EventEmitterTest
 *
 * @author debop
 */
abstract class AbstractEventEmitterTest<E: EventEmitter>(
    private val specialDay: Weekday?,
    private val event: Event?,
    private val factoryWithDefaultObserver: (EventObserver) -> E,
    private val factoryWithoutDefaultObserver: () -> E
) {

    val defaultObserver = mockk<EventObserver>(relaxUnitFun = true)
    val observer1 = mockk<EventObserver>(relaxUnitFun = true)
    val observer2 = mockk<EventObserver>(relaxUnitFun = true)


    @Test
    fun `test all weekdays`() {
        testAllDaysWithoutDefaultObserver(specialDay, event)
        testAllDaysWithDefaultObserver(specialDay, event)
    }

    private fun testAllDaysWithoutDefaultObserver(specialDay: Weekday?, event: Event?) {
        clearMocks(defaultObserver, observer1, observer2)

        val emitter = factoryWithoutDefaultObserver()
        emitter.registerObserver(observer1)
        emitter.registerObserver(observer2)

        testAllDays(specialDay, event, emitter, observer1, observer2)
    }

    private fun testAllDaysWithDefaultObserver(specialDay: Weekday?, event: Event?) {
        clearMocks(defaultObserver, observer1, observer2)

        val emitter: E = factoryWithDefaultObserver(defaultObserver)
        emitter.registerObserver(observer1)
        emitter.registerObserver(observer2)

        testAllDays(specialDay, event, emitter, defaultObserver, observer1, observer2)
    }

    /**
     * Pass each week of the day, day by day to the event emitter and verify of the given observers
     * received the correct event on the special day.
     *
     * @param specialDay The special day on which an event is emitted
     * @param event      The expected event emitted by the test object
     * @param emitter    The event emitter
     * @param observers  The registered observer mocks
     */
    private fun testAllDays(specialDay: Weekday?, event: Event?, emitter: E, vararg observers: EventObserver) {
        Weekday.values().forEach { weekday ->

            // Pass each week of the day, day by day to the event emitter
            emitter.timePasses(weekday)

            if (weekday == specialDay) {
                // On a special day, every observer should have received the event
                observers.forEach { observer ->
                    if (event == null) {
                        verify(exactly = 1) { observer.onEvent(null) }
                    } else {
                        verify(exactly = 1) { observer.onEvent(refEq(event)) }
                    }
                }
            }
        }
    }
}