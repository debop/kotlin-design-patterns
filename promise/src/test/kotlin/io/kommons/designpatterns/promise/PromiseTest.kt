package io.kommons.designpatterns.promise

import io.kommons.logging.KLogging
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

class PromiseTest {

    companion object: KLogging()

    private lateinit var executor: Executor
    private lateinit var promise: Promise<Int>

    @BeforeEach
    fun setup() {
        executor = Executors.newSingleThreadExecutor()
        promise = Promise()
    }

    @Test
    fun `promise is fulfilled with the resultant value of executing the task`() {
        promise.fulfillInAsync(executor, NumberCrunchingTask())

        promise.get() shouldEqual NumberCrunchingTask.CRUNCHED_NUMBER
        promise.isDone.shouldBeTrue()
        promise.isCancelled.shouldBeFalse()
    }

    @Test
    fun `promise is fulfilled with an exception if task throws an exception`() {
        `waiting forever for promise to be fullfilled`()
        `waiting some time for promise to be fulfilled`()
    }

    private fun `waiting forever for promise to be fullfilled`() {
        val promise = Promise<Int>().fulfillInAsync(executor) {
            throw RuntimeException("Barf!")
        }

        try {
            promise.get()
            fail { "Fetching promise should result in exception if the task threw an exception." }
        } catch (e: ExecutionException) {
            promise.isDone.shouldBeTrue()
            promise.isCancelled.shouldBeFalse()
        }

        try {
            promise.get(1000, TimeUnit.SECONDS)
            fail { "Fetching promise should result in exception if the task threw an exception." }
        } catch (e: ExecutionException) {
            promise.isDone.shouldBeTrue()
            promise.isCancelled.shouldBeFalse()
        }
    }

    private fun `waiting some time for promise to be fulfilled`() {
        val promise = Promise<Int>().fulfillInAsync(executor) {
            throw RuntimeException("Barf!")
        }

        try {
            promise.get(1000, TimeUnit.SECONDS)
            fail { "Fetching promise should result in exception if the task threw an exception." }
        } catch (e: ExecutionException) {
            promise.isDone.shouldBeTrue()
            promise.isCancelled.shouldBeFalse()
        }

        try {
            promise.get()
            fail { "Fetching promise should result in exception if the task threw an exception." }
        } catch (e: ExecutionException) {
            promise.isDone.shouldBeTrue()
            promise.isCancelled.shouldBeFalse()
        }
    }

    @Test
    fun `dependent promise if fulfill after the consumer consumes the result of this promise`() {
        val dependentPromise = promise
            .fulfillInAsync(executor, NumberCrunchingTask())
            .thenAccept { it shouldEqual NumberCrunchingTask.CRUNCHED_NUMBER }

        dependentPromise.get()
        dependentPromise.isDone.shouldBeTrue()
        dependentPromise.isCancelled.shouldBeFalse()
    }

    @Test
    fun `dependent promise is fulfilled with an exception if consumer throws an exception`() {
        val dependentPromise = promise
            .fulfillInAsync(executor, NumberCrunchingTask())
            .thenAccept { throw java.lang.RuntimeException("Barf!") }

        with(dependentPromise) {
            try {
                get()
                fail { "Fetching dependent promise should result in exception if action threw an exception." }
            } catch (e: ExecutionException) {
                promise.isDone.shouldBeTrue()
                promise.isCancelled.shouldBeFalse()
            }

            try {
                get(1000, TimeUnit.SECONDS)
                fail { "Fetching dependent promise should result in exception if action threw an exception." }
            } catch (e: ExecutionException) {
                promise.isDone.shouldBeTrue()
                promise.isCancelled.shouldBeFalse()
            }
        }
    }

    @Test
    fun `dependent promise if fulfill after the function transforms the result of this promise`() {
        val dependentPromise = promise
            .fulfillInAsync(executor, NumberCrunchingTask())
            .thenApply {
                it shouldEqual NumberCrunchingTask.CRUNCHED_NUMBER
                it.toString()
            }

        dependentPromise.get() shouldEqual NumberCrunchingTask.CRUNCHED_NUMBER.toString()
        dependentPromise.isDone.shouldBeTrue()
        dependentPromise.isCancelled.shouldBeFalse()
    }

    @Test
    fun `dependent promise is fulfilled with an exception if the function throws an exception`() {
        val dependentPromise = promise
            .fulfillInAsync(executor, NumberCrunchingTask())
            .thenApply { throw java.lang.RuntimeException("Barf!") }

        with(dependentPromise) {
            try {
                get()
                fail { "Fetching dependent promise should result in exception if action threw an exception." }
            } catch (e: ExecutionException) {
                promise.isDone.shouldBeTrue()
                promise.isCancelled.shouldBeFalse()
            }

            try {
                get(1000, TimeUnit.SECONDS)
                fail { "Fetching dependent promise should result in exception if action threw an exception." }
            } catch (e: ExecutionException) {
                promise.isDone.shouldBeTrue()
                promise.isCancelled.shouldBeFalse()
            }
        }
    }

    @Test
    fun `fetching an already fulfilled promise returns the fulfilled value immediatly`() {
        val promise = Promise<Int>().apply {
            fulfill(NumberCrunchingTask.CRUNCHED_NUMBER)
        }

        promise.get(1000, TimeUnit.SECONDS) shouldEqual NumberCrunchingTask.CRUNCHED_NUMBER
    }

    @Test
    fun `exception handler is called when promise is fulfilled exceptionally`() {
        val promise = Promise<Int>()
        val exceptionHandler = mockk<Consumer<Throwable?>>(relaxUnitFun = true)
        promise.onError { exceptionHandler.accept(it) }

        val exception = Exception("barf!")
        promise.fulfillExceptionally(exception)

        verify(exactly = 1) { exceptionHandler.accept(exception) }
    }

    private class NumberCrunchingTask: () -> Int {

        companion object {
            const val CRUNCHED_NUMBER = Int.MAX_VALUE
        }

        override fun invoke(): Int {
            Thread.sleep(100L)
            return CRUNCHED_NUMBER
        }
    }
}