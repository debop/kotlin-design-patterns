package io.kommons.designpatterns.promise

import io.kommons.logging.KLogging
import io.kommons.logging.warn
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

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

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    @Volatile
    private var state: Int = RUNNING
    private var value: T? = null
    private var exception: Exception? = null

    open fun fulfill(value: T?) {
        this.value = value
        this.state = COMPLETED

        lock.withLock {
            condition.signalAll()
        }
    }

    open fun fulfillExceptionally(exception: Exception?) {
        this.exception = exception
        this.state = FAILED

        lock.withLock {
            condition.signalAll()
        }
    }

    override fun cancel(mayInterruptIfRunning: Boolean): Boolean = false

    override fun isCancelled(): Boolean = false

    override fun isDone(): Boolean = state > RUNNING

    override fun get(): T? {
        lock.withLock {
            while (state == RUNNING) {
                condition.await()
            }
        }
        if (state == COMPLETED) {
            return value
        }
        throw ExecutionException(exception)
    }

    override fun get(timeout: Long, unit: TimeUnit): T? {
        lock.withLock {
            while (state == RUNNING) {
                try {
                    condition.await(timeout, unit)
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