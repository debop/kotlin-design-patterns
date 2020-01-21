package io.kommons.designpatterns.event.aggregator

import io.kommons.designpatterns.event.aggregator.emitters.AbstractEventEmitterTest
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KingsHandTest: AbstractEventEmitterTest<KingsHand>(null,
                                                         null,
                                                         { KingsHand(it) },
                                                         { KingsHand() }) {

    private val observer = mockk<EventObserver>(relaxUnitFun = true)

    @BeforeEach
    fun setup() {
        clearMocks(observer)
    }

    @Test
    fun `path through`() {
        val kingsHand = KingsHand(observer)

        verify(exactly = 0) { observer.onEvent(any()) }

        Event.values().forEach { event ->
            kingsHand.onEvent(event)
            verify(exactly = 1) { observer.onEvent(refEq(event)) }
        }
    }
}