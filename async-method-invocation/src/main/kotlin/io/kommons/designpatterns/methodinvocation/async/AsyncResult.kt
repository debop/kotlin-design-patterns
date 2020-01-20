package io.kommons.designpatterns.methodinvocation.async

import java.util.concurrent.ExecutionException

/**
 * AsyncResult interface
 * @param <T> parameter returned when getValue is invoked
 */
interface AsyncResult<T> {

    /**
     * Status of the async task execution.
     *
     * @return <code>true</code> if execution is completed or failed
     */
    fun isCompleted(): Boolean

    /**
     * Gets the value of completed async task.
     *
     * @return evaluated value or throws ExecutionException if execution has failed
     * @throws ExecutionException if execution has failed, containing the root cause
     * @throws IllegalStateException if execution is not completed
     */
    @Throws(ExecutionException::class, IllegalStateException::class)
    fun getValue(): T?

    /**
     * Blocks the current thread until the async task is completed.
     *
     * @throws InterruptedException if the execution is interrupted
     */
    @Throws(InterruptedException::class)
    fun await()

}