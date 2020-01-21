package io.kommons.designpatterns.event.coroutines

import io.kommons.designpatterns.event.ThreadCompleteListener
import io.kommons.designpatterns.event.exception.EventDoesNotExistException
import io.kommons.designpatterns.event.exception.LongRunningEventException
import io.kommons.designpatterns.event.exception.MaxNumOfEventsAllowedException
import io.kommons.logging.KLogging
import io.kommons.logging.debug
import kotlinx.coroutines.runBlocking
import java.util.Random
import java.util.concurrent.ConcurrentHashMap

/**
 * CoEventManager
 *
 * @author debop
 */
class CoEventManager: ThreadCompleteListener {

    companion object: KLogging() {
        const val MAX_RUNNING_EVENTS = 1000
        const val MIN_ID = 1
        const val MAX_ID = MAX_RUNNING_EVENTS
        const val MAX_EVENT_TIME = 1800     // in seconds / 30 minutes

        private const val DOES_NOT_EXIST = " does not exist."

        private val rand = Random()
    }

    private var currentlyRunningSyncEvent = -1
    val eventPool = ConcurrentHashMap<Int, CoEvent>(MAX_RUNNING_EVENTS)

    /**
     * Create an Asynchronous event.
     *
     * @param eventTime Time an event should run for.
     * @return eventId
     * @throws MaxNumOfEventsAllowedException When too many events are running at a time.
     * @throws LongRunningEventException Long running events are not allowed in the app.
     */
    @Throws(MaxNumOfEventsAllowedException::class, LongRunningEventException::class)
    fun createAsync(eventTime: Int): Int {
        if (eventPool.size >= MAX_RUNNING_EVENTS) {
            throw MaxNumOfEventsAllowedException("Too many events are running at the moment. Please try again later.")
        }

        if (eventTime >= MAX_EVENT_TIME) {
            throw LongRunningEventException("Maximum event time allowed is $MAX_EVENT_TIME seconds. Please try again")
        }

        val newEventId = generateId()
        eventPool.computeIfAbsent(newEventId) { eventId ->
            CoEvent(eventId, eventTime).also { it.addListener(this) }
        }
        return newEventId
    }

    /**
     * Starts event.
     *
     * @param eventId The event that needs to be started.
     * @throws EventDoesNotExistException If event does not exist in our eventPool.
     */
    @Throws(EventDoesNotExistException::class)
    suspend fun start(eventId: Int) {
        log.debug { "Start event [$eventId]" }
        eventPool[eventId]?.start() ?: throw EventDoesNotExistException("Event[$eventId] $DOES_NOT_EXIST")
    }

    /**
     * Stops event.
     *
     * @param eventId The event that needs to be stopped.
     * @throws EventDoesNotExistException If event does not exist in our eventPool.
     */
    @Throws(EventDoesNotExistException::class)
    suspend fun cancel(eventId: Int) {
        eventPool[eventId]?.run {
            if (eventId == currentlyRunningSyncEvent) {
                currentlyRunningSyncEvent = -1
            }
            stop()
            eventPool.remove(eventId)
        } ?: throw EventDoesNotExistException("Event[$eventId] $DOES_NOT_EXIST")
    }

    /**
     * Get status of a running event.
     *
     * @param eventId The event to inquire status of.
     * @throws EventDoesNotExistException If event does not exist in our eventPool.
     */
    @Throws(EventDoesNotExistException::class)
    suspend fun status(eventId: Int) {
        eventPool[eventId]?.status() ?: throw EventDoesNotExistException("Event[$eventId] $DOES_NOT_EXIST")
    }

    /**
     * Gets status of all running events.
     */
    suspend fun statusOfAllEvents() {
        eventPool.forEach { (_, event) ->
            event.status()
        }
    }

    /**
     * Stop all running events.
     */
    suspend fun shutdown() {
        eventPool.forEach { (_, event) ->
            event.stop()
        }
        eventPool.clear()
        currentlyRunningSyncEvent = -1
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive. The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     */
    private fun generateId(): Int {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        var randomNum = rand.nextInt((MAX_ID - MIN_ID) + 1) + MIN_ID
        while (eventPool.containsKey(randomNum)) {
            randomNum = rand.nextInt((MAX_ID - MIN_ID) + 1) + MIN_ID
        }
        return randomNum
    }

    override fun completedEventHandler(eventId: Int) {
        log.debug { "comleted event [$eventId]" }
        eventPool[eventId]?.run {
            runBlocking {
                status()
            }
            eventPool.remove(eventId)
        }
    }

    fun numOfCurrentlyRunningSyncEvent(): Int = currentlyRunningSyncEvent
}