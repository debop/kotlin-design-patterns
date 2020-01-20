package io.kommons.designpatterns.methodinvocation.async

import io.kommons.designpatterns.methodinvocation.async.ThreadAsyncExecutor.CompletableResult.TaskStatus.COMPLETED
import io.kommons.designpatterns.methodinvocation.async.ThreadAsyncExecutor.CompletableResult.TaskStatus.FAILED
import io.kommons.designpatterns.methodinvocation.async.ThreadAsyncExecutor.CompletableResult.TaskStatus.RUNNING
import io.kommons.logging.KLogging
import io.kommons.logging.debug
import io.kommons.logging.trace
import java.util.concurrent.ExecutionException
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

/**
 * Implementation of async executor that creates a new thread for every task.
 */
class ThreadAsyncExecutor: AsyncExecutor {

    companion object: KLogging()

    /** Index for thread naming */
    private val idx = AtomicInteger(0)

    override fun <T> startProcess(task: () -> T, callback: AsyncCallback<T>?): AsyncResult<T> {
        val result = CompletableResult(callback)

        thread(start = true, name = "executor-${idx.incrementAndGet()}") {
            try {
                log.trace { "Start task ..." }
                result.setValue(task.invoke())
            } catch (e: Exception) {
                result.setException(e)
            }
        }

        return result
    }

    override fun <T> endProcess(asyncResult: AsyncResult<T>): T {
        if (!asyncResult.isCompleted()) {
            log.trace { "Await asyncResult ..." }
            asyncResult.await()
        }
        return asyncResult.getValue()!!
    }

    /**
     * Simple implementation of async result that allows completing it successfully with a value or exceptionally with an
     * exception. A really simplified version from its real life cousins FutureTask and CompletableFuture.
     *
     * @see java.util.concurrent.FutureTask
     * @see java.util.concurrent.CompletableFuture
     */
    private class CompletableResult<T>(private var callback: AsyncCallback<T>? = null): AsyncResult<T> {

        enum class TaskStatus(val code: Int) {
            UNKNOWN(0), RUNNING(1), FAILED(2), COMPLETED(3);
        }

        private val lock = ReentrantLock()

        @Volatile
        var state = RUNNING

        private var result: T? = null
        private var exception: Throwable? = null

        /**
         * Sets the value from successful execution and executes callback if available. Notifies any thread waiting for
         * completion.
         *
         * @param value value of the evaluated task
         */
        fun setValue(value: T?) {
            this.result = value
            this.state = COMPLETED
            this.callback?.onComplete(this.result, null)
        }

        /**
         * Sets the exception from failed execution and executes callback if available. Notifies any thread waiting for
         * completion.
         *
         * @param exception exception of the failed task
         */
        fun setException(ex: Throwable?) {
            this.exception = ex
            this.state = FAILED
            this.callback?.onComplete(null, exception)
        }

        override fun isCompleted(): Boolean {
            log.debug { "Current state=$state" }
            return state.code > RUNNING.code
        }

        override fun getValue(): T? {
            return when (state) {
                COMPLETED -> result
                FAILED    -> throw ExecutionException(exception)
                else      -> error("Execution not completed yet")
            }
        }

        override fun await() {
            log.trace { "Await task finished..." }
            while (state.code <= RUNNING.code) {
                Thread.sleep(1)
            }
        }
    }
}
