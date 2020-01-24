package io.kommons.designpatterns.promise

import java.util.concurrent.Executor
import java.util.function.Consumer

/**
 * A Promise represents a proxy for a value not necessarily known when the promise is created. It
 * allows you to associate dependent promises to an asynchronous action's eventual success value or
 * failure reason. This lets asynchronous methods return values like synchronous methods: instead of
 * the final value, the asynchronous method returns a promise of having a value at some point in the
 * future.
 *
 * @param <T> type of result.
 */
class Promise<T: Any>: PromiseSupport<T>() {

    private var fulfillmentAction: Runnable? = null
    private var exceptionHandler: Consumer<Throwable?>? = null


    override fun fulfill(value: T?) {
        super.fulfill(value)
        postFulfillment()
    }

    override fun fulfillExceptionally(exception: Exception?) {
        super.fulfillExceptionally(exception)
        handleException(exception)
        postFulfillment()
    }

    private fun handleException(exception: Exception?) {
        exceptionHandler?.accept(exception)
    }

    private fun postFulfillment() {
        fulfillmentAction?.run()
    }

    /**
     * Executes the task using the executor in other thread and fulfills the promise returned once the
     * task completes either successfully or with an exception.
     *
     * @param task     the task that will provide the value to fulfill the promise.
     * @param executor the executor in which the task should be run.
     * @return a promise that represents the result of running the task provided.
     */
    fun fulfillInAsync(executor: Executor, task: () -> T): Promise<T> = apply {
        executor.execute {
            try {
                fulfill(task.invoke())
            } catch (e: Exception) {
                fulfillExceptionally(e)
            }
        }
    }

    /**
     * Returns a new promise that, when this promise is fulfilled normally, is fulfilled with result
     * of this promise as argument to the action provided.
     *
     * @param action action to be executed.
     * @return a new promise.
     */
    fun thenAccept(action: (T?) -> Unit): Promise<Unit> {
        return Promise<Unit>().also {
            fulfillmentAction = ConsumerAction(this, it, action)
        }
    }

    /**
     * Set the exception handler on this promise.
     *
     * @param exceptionHandler a consumer that will handle the exception occurred while fulfilling the
     *                         promise.
     * @return this
     */
    fun onError(exceptionHandler: (Throwable?) -> Unit): Promise<T> = apply {
        this.exceptionHandler = Consumer { exceptionHandler.invoke(it) }
    }

    /**
     * Returns a new promise that, when this promise is fulfilled normally, is fulfilled with result
     * of this promise as argument to the function provided.
     *
     * @param transform function to be executed.
     * @return a new promise.
     */
    fun <V: Any> thenApply(transform: (T?) -> V?): Promise<V> {
        return Promise<V>().also {
            fulfillmentAction = TransformAction(this, it, transform)
        }
    }

    /**
     * Accesses the value from source promise and calls the consumer, then fulfills the destination promise.
     */
    private inner class ConsumerAction(private val src: Promise<T>,
                                       private val dest: Promise<Unit>,
                                       private val action: (T?) -> Unit): Runnable {
        override fun run() {
            try {
                action.invoke(src.get())
                dest.fulfill(null)
            } catch (e: Throwable) {
                dest.fulfillExceptionally(e.cause as? Exception)
            }
        }
    }

    /**
     * Accesses the value from source promise, then fulfills the destination promise using the
     * transformed value. The source value is transformed using the transformation function.
     */
    private inner class TransformAction<V: Any>(private val src: Promise<T>,
                                                private val dest: Promise<V>,
                                                private val transform: (T?) -> V?): Runnable {
        override fun run() {
            try {
                dest.fulfill(transform(src.get()))
            } catch (e: Throwable) {
                dest.fulfillExceptionally(e.cause as? Exception)
            }
        }
    }

}