package io.kommons.designpatterns.event.coroutines

import io.kommons.logging.KLogging
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test

class EventCoroutinesTest {

    companion object: KLogging()

    @Test
    fun `execute coroutine event`() = runBlocking<Unit> {
        val manager = CoEventManager()
        manager.eventPool.isEmpty().shouldBeTrue()

        val eventId = manager.createAsync(2)  // 5 seconds
        manager.start(eventId)

        delay(1)

        manager.eventPool.size shouldEqualTo 1
        manager.numOfCurrentlyRunningSyncEvent() shouldEqualTo -1

        delay(10)

        manager.cancel(eventId)
        manager.eventPool.isEmpty().shouldBeTrue()
    }

    @Test
    fun `fully coroutines events`() = runBlocking<Unit> {
        val manager = CoEventManager()
        val eventTime = 1 // seconds

        val eventId1 = manager.createAsync(eventTime)
        val eventId2 = manager.createAsync(eventTime)
        val eventId3 = manager.createAsync(eventTime)

        manager.eventPool.size shouldEqualTo 3

        manager.start(eventId1)
        manager.start(eventId2)
        manager.start(eventId3)

        // delay(1)

        manager.status(eventId1)
        manager.status(eventId2)
        manager.status(eventId3)

        delay((eventTime + 1) * 1000L)

        manager.eventPool.isEmpty().shouldBeTrue()
    }
}