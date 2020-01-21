package io.kommons.designpatterns.event.async

import io.kommons.designpatterns.event.exception.InvalidOperationException
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.fail

class EventAsynchronousTest {

    companion object: KLogging()

    @Test
    fun `execute asynchronous event`() {
        val eventManager = EventManager()

        val eventId = eventManager.createAsync(5)
        eventManager.start(eventId)

        eventManager.eventPool.size shouldEqualTo 1
        eventManager.numOfCurrentlyRunningSyncEvent() shouldEqualTo -1

        Thread.sleep(100)

        eventManager.cancel(eventId)
        eventManager.eventPool.isEmpty().shouldBeTrue()
    }

    @Test
    fun `execute synchronous event`() {
        val eventManager = EventManager()

        val eventId = eventManager.create(60)
        eventManager.start(eventId)

        eventManager.eventPool.size shouldEqualTo 1
        eventManager.numOfCurrentlyRunningSyncEvent() shouldEqualTo eventId

        eventManager.cancel(eventId)
        eventManager.eventPool.isEmpty().shouldBeTrue()
        eventManager.numOfCurrentlyRunningSyncEvent() shouldEqualTo -1
    }

    @Test
    fun `unsuccessful synchronous event`() {
        val eventManager = EventManager()

        assertThrows<InvalidOperationException> {
            val eventId = eventManager.create(60)
            eventManager.start(eventId)

            eventManager.create(60)
            fail("이 코드는 실행되면 안됩니다")
        }
    }

    @Test
    fun `fully synchronous events`() {
        val eventManager = EventManager()
        val eventTime = 1

        val eventId = eventManager.create(eventTime)
        eventManager.eventPool.size shouldEqualTo 1
        eventManager.start(eventId)

        val startTime = System.currentTimeMillis()
        val endTime = startTime + (eventTime + 2) * 1000

        while (System.currentTimeMillis() < endTime) {
            // Nothing to do
        }
        eventManager.eventPool.isEmpty().shouldBeTrue()
    }

    @Test
    fun `fully asynchronous events`() {
        val eventManager = EventManager()
        val eventTime = 1

        val eventId1 = eventManager.createAsync(eventTime)
        val eventId2 = eventManager.createAsync(eventTime)
        val eventId3 = eventManager.createAsync(eventTime)

        eventManager.eventPool.size shouldEqualTo 3

        eventManager.start(eventId1)
        eventManager.start(eventId2)
        eventManager.start(eventId3)

        eventManager.status(eventId1)
        eventManager.status(eventId2)
        eventManager.status(eventId3)

        Thread.sleep((eventTime + 1) * 1000L)

        eventManager.eventPool.isEmpty().shouldBeTrue()
    }
}