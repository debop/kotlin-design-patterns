package io.kommons.designpatterns.promise

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.Executor
import java.util.concurrent.Executors

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