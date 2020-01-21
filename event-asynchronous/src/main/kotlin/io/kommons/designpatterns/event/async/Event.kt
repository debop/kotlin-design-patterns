package io.kommons.designpatterns.event.async

import io.kommons.designpatterns.event.ThreadCompleteListener
import io.kommons.logging.KLogging
import io.kommons.logging.debug
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Each Event runs as a separate/individual thread.
 *
 * @author debop
 */
class Event(val eventId: Int,
            val eventTime: Int,
            override val isSynchronous: Boolean): IEvent, Runnable {

    companion object: KLogging()

    private var thread: Thread? = null
    private val isCompleted = AtomicBoolean(false)
    private val eventListeners = mutableSetOf<ThreadCompleteListener>()

    override fun start() {
        thread = Thread(this)
        thread?.start()
    }

    override fun stop() {
        thread?.interrupt()
    }

    override fun status() {
        if (isCompleted.get()) {
            log.debug { "[$eventId] is done." }
        } else {
            log.debug { "[$eventId] is not done." }
        }
    }

    override fun run() {
        val startTime = System.currentTimeMillis()
        val endTime = startTime + (eventTime * 1000L)

        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                // Ignore interrupted exception
            }
        }
        isCompleted.set(true)
        completed()
    }

    fun addListener(listener: ThreadCompleteListener) {
        eventListeners.add(listener)
    }

    fun removeListener(listener: ThreadCompleteListener) {
        eventListeners.remove(listener)
    }

    private fun completed() {
        eventListeners.forEach { listener ->
            runCatching {
                listener.completedEventHandler(eventId)
            }
        }
    }
}