package io.kommons.designpatterns.promise

import io.kommons.logging.KLogging
import io.kommons.logging.warn
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

/**
 * A really simplified implementation of future that allows completing it successfully with a value
 * or exceptionally with an exception.
 */
open class PromiseSupport<T: Any>: Future<T> {

    companion object: KLogging() {
        private const val RUNNING = 1
        private const val FAILED = 2
        private const val COMPLETED = 3
    }

    private val lock = Object()

    @Volatile
    private var state: Int = RUNNING
    private var value: T? = null
    private var exception: Exception? = null

    open fun fulfill(value: T?) {
        this.value = value
        this.state = COMPLETED
        synchronized(lock) {
            lock.notifyAll()
        }
    }

    open fun fulfillExceptionally(exception: Exception?) {
        this.exception = exception
        this.state = FAILED
        synchronized(lock) {
            lock.notifyAll()
        }
    }

    override fun cancel(mayInterruptIfRunning: Boolean): Boolean = false

    override fun isCancelled(): Boolean = false

    override fun isDone(): Boolean = state > RUNNING

    override fun get(): T? {
        synchronized(lock) {
            while (state == RUNNING) {
                lock.wait()
            }
        }
        if (state == COMPLETED) {
            return value
        }
        throw ExecutionException(exception)
    }

    override fun get(timeout: Long, unit: TimeUnit): T? {
        synchronized(lock) {
            while (state == RUNNING) {
                try {
                    lock.wait(unit.toMillis(timeout))
                } catch (e: InterruptedException) {
                    log.warn(e) { "Interrupted" }
                    Thread.currentThread().interrupt()
                }
            }
        }
        if (state == COMPLETED) {
            return value
        }
        throw ExecutionException(exception)
    }
}