package io.kommons.designpatterns.event.coroutines

import io.kommons.designpatterns.event.ThreadCompleteListener
import io.kommons.logging.KLogging
import io.kommons.logging.debug
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * CoEvent
 *
 * @author debop
 */
class CoEvent(val eventId: Int, val eventTime: Int): ICoEvent {

    companion object: KLogging()

    private val scope = CoroutineScope(Dispatchers.Default)

    private var job: Job? = null
    private val isCompleted: Boolean = job?.isCompleted ?: false
    private val eventListeners = mutableSetOf<ThreadCompleteListener>()


    override suspend fun start() {
        job = scope.launch { invoke() }
    }

    override suspend fun stop() {
        log.debug { "Stopping event job..." }
        job?.cancelAndJoin()
    }

    override suspend fun status() {
        if (isCompleted) {
            log.debug { "[$eventId] is done." }
        } else {
            log.debug { "[$eventId] is not done." }
        }
    }

    suspend fun invoke() {
        val endTime = System.currentTimeMillis() + (eventTime * 1000L)

        log.debug { "Run event until $endTime" }

        while (job?.isActive == true && System.currentTimeMillis() < endTime) {
            delay(100)
            log.debug { "Event[$eventId] do work ..." }
        }
        log.debug { "Event finished" }
        completed()
    }

    fun addListener(listener: ThreadCompleteListener) {
        eventListeners.add(listener)
    }

    fun removeListener(listener: ThreadCompleteListener) {
        eventListeners.remove(listener)
    }

    private fun completed() {
        eventListeners.forEach {
            it.completedEventHandler(eventId)
        }
    }
}