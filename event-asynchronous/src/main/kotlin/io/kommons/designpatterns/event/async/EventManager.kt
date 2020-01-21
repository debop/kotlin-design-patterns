package io.kommons.designpatterns.event.async

import io.kommons.designpatterns.event.ThreadCompleteListener
import io.kommons.designpatterns.event.exception.EventDoesNotExistException
import io.kommons.designpatterns.event.exception.InvalidOperationException
import io.kommons.designpatterns.event.exception.LongRunningEventException
import io.kommons.designpatterns.event.exception.MaxNumOfEventsAllowedException
import io.kommons.logging.KLogging
import java.util.Random
import java.util.concurrent.ConcurrentHashMap

/**
 * EventManager handles and maintains a pool of event threads. {@link Event} threads are created upon user request.
 * There are two types of events; Asynchronous and Synchronous.
 * There can be multiple Asynchronous events running at once but only one Synchronous event running at a time.
 * Currently supported event operations are: start, stop, and getStatus.
 * Once an event is complete, it then notifies EventManager through a listener.
 * The EventManager then takes the event out of the pool.
 *
 * @author debop
 */
class EventManager: ThreadCompleteListener {

    companion object: KLogging() {
        const val MAX_RUNNING_EVENTS = 1000
        const val MIN_ID = 1
        const val MAX_ID = MAX_RUNNING_EVENTS
        const val MAX_EVENT_TIME = 1800     // in seconds / 30 minutes

        private const val DOES_NOT_EXIST = " does not exist."
    }

    private val rand = Random(1)

    private var currentlyRunningSyncEvent = -1
    val eventPool = ConcurrentHashMap<Int, IEvent>(MAX_RUNNING_EVENTS)

    /**
     * Create a Synchronous event.
     *
     * @param eventTime Time an event should run for.
     * @return eventId
     * @throws MaxNumOfEventsAllowedException When too many events are running at a time.
     * @throws InvalidOperationException No new synchronous events can be created when one is already running.
     * @throws LongRunningEventException Long running events are not allowed in the app.
     */
    @Throws(MaxNumOfEventsAllowedException::class,
            InvalidOperationException::class,
            LongRunningEventException::class)
    fun create(eventTime: Int): Int {
        if (currentlyRunningSyncEvent != -1) {
            throw InvalidOperationException("Event [$currentlyRunningSyncEvent] is still running. " +
                                            "Please wait until it finished and try again.")
        }
        val eventId = createEvent(eventTime, true)
        currentlyRunningSyncEvent = eventId

        return eventId
    }

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
        return createEvent(eventTime, false)
    }

    private fun createEvent(eventTime: Int, isSynchronous: Boolean): Int {
        if (eventPool.size >= MAX_RUNNING_EVENTS) {
            throw MaxNumOfEventsAllowedException("Too many events are running at the moment. Please try again later.")
        }

        if (eventTime >= MAX_EVENT_TIME) {
            throw LongRunningEventException("Maximum event time allowed is $MAX_EVENT_TIME seconds. Please try again")
        }

        val newEventId = generateId()
        eventPool.computeIfAbsent(newEventId) { eventId ->
            Event(eventId, eventTime, isSynchronous).also {
                it.addListener(this)
            }
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
    fun start(eventId: Int) {
        eventPool[eventId]?.start() ?: throw EventDoesNotExistException("Event[$eventId] $DOES_NOT_EXIST")
    }

    /**
     * Stops event.
     *
     * @param eventId The event that needs to be stopped.
     * @throws EventDoesNotExistException If event does not exist in our eventPool.
     */
    @Throws(EventDoesNotExistException::class)
    fun cancel(eventId: Int) {
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
    fun status(eventId: Int) {
        eventPool[eventId]?.status() ?: throw EventDoesNotExistException("Event[$eventId] $DOES_NOT_EXIST")
    }

    /**
     * Gets status of all running events.
     */
    fun statusOfAllEvents() {
        eventPool.forEach { (_: Int, event: IEvent) ->
            event.status()
        }
    }

    /**
     * Stop all running events.
     */
    fun shutdown() {
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
        eventPool[eventId]?.run {
            status()
            if (this.isSynchronous) {
                currentlyRunningSyncEvent = -1
            }
            eventPool.remove(eventId)
        }
    }

    fun numOfCurrentlyRunningSyncEvent(): Int = currentlyRunningSyncEvent
}