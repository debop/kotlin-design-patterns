package io.kommons.designpatterns.methodinvocation.asyc

import io.kommons.designpatterns.methodinvocation.async.AsyncCallback
import io.kommons.designpatterns.methodinvocation.async.ThreadAsyncExecutor
import io.kommons.logging.KLogging
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.assertTimeout
import java.time.Duration

/**
 * ThreadAsyncExecutorTest
 *
 * @author debop
 */
class ThreadAsyncExecutorTest {

    companion object: KLogging()

    val executor = ThreadAsyncExecutor()

    @Test
    fun `successful task without callback`() {
        val result = Any()
        val task = mockk<() -> Any>(relaxUnitFun = true)
        every { task.invoke() } returns result

        assertTimeout(Duration.ofMillis(3000)) {
            val asyncResult = executor.startProcess(task)
            asyncResult.shouldNotBeNull()
            asyncResult.await()
            asyncResult.isCompleted().shouldBeTrue()

            verify(exactly = 1) { task.invoke() }
            asyncResult.getValue() shouldEqual result
        }
    }

    @Test
    fun `successful task with callback`() {
        val result = Any()
        val task = mockk<() -> Any>(relaxUnitFun = true)
        every { task.invoke() } returns result

        val callback = mockk<AsyncCallback<Any>>(relaxUnitFun = true)

        assertTimeout(Duration.ofMillis(3000)) {

            val asyncResult = executor.startProcess(task, callback)
            asyncResult.shouldNotBeNull()
            asyncResult.await()
            asyncResult.isCompleted().shouldBeTrue()

            verify(exactly = 1) { task.invoke() }
            asyncResult.getValue() shouldEqual result

            verify(exactly = 1) { callback.onComplete(result, null) }
        }
    }

    @Test
    fun `long running task without callback`() {
        val result = Any()
        val task = mockk<() -> Any>(relaxUnitFun = true)
        every { task.invoke() } answers {
            Thread.sleep(1500)
            result
        }

        assertTimeout(Duration.ofMillis(5000)) {
            val asyncResult = executor.startProcess(task)
            asyncResult.shouldNotBeNull()
            asyncResult.isCompleted().shouldBeFalse()

            assertThrows<IllegalStateException> {
                asyncResult.getValue()
            }

            asyncResult.await()
            asyncResult.isCompleted().shouldBeTrue()

            verify(exactly = 1) { task.invoke() }
            asyncResult.getValue() shouldEqual result
        }
    }

    @Test
    fun `long running task with callback`() {
        val result = Any()
        val task = mockk<() -> Any>(relaxUnitFun = true)
        every { task.invoke() } answers {
            Thread.sleep(1500)
            result
        }

        val callback = mockk<AsyncCallback<Any>>(relaxUnitFun = true)

        assertTimeout(Duration.ofMillis(5000)) {
            val asyncResult = executor.startProcess(task, callback)
            asyncResult.shouldNotBeNull()
            asyncResult.isCompleted().shouldBeFalse()

            assertThrows<IllegalStateException> {
                asyncResult.getValue()
            }

            asyncResult.await()
            asyncResult.isCompleted().shouldBeTrue()

            verify(exactly = 1) { task.invoke() }
            asyncResult.getValue() shouldEqual result

            verify(exactly = 1) { callback.onComplete(result, null) }
        }
    }

    @Test
    fun `call endProcess`() {
        val result = Any()
        val task = mockk<() -> Any>(relaxUnitFun = true)
        every { task.invoke() } answers {
            Thread.sleep(1500)
            result
        }

        assertTimeout(Duration.ofMillis(5000)) {
            val asyncResult = executor.startProcess(task)
            asyncResult.shouldNotBeNull()
            asyncResult.isCompleted().shouldBeFalse()

            assertThrows<IllegalStateException> {
                asyncResult.getValue()
            }

            executor.endProcess(asyncResult) shouldEqual result
            asyncResult.isCompleted().shouldBeTrue()

            verify(exactly = 1) { task.invoke() }
            asyncResult.getValue() shouldEqual result
        }
    }

    @Test
    fun `executor multi tasks`() {
        val task1 = mockk<() -> Int>(relaxUnitFun = true)
        every { task1.invoke() } answers {
            Thread.sleep(1500)
            21
        }
        val task2 = mockk<() -> Int>(relaxUnitFun = true)
        every { task2.invoke() } answers {
            Thread.sleep(1500)
            23
        }

        assertTimeout(Duration.ofMillis(3000)) {
            val asyncResult1 = executor.startProcess(task1)
            val asyncResult2 = executor.startProcess(task2)

            Thread.sleep(100)

            verify(exactly = 1) { task1.invoke() }
            verify(exactly = 1) { task2.invoke() }

            asyncResult1.await()
            asyncResult2.await()

            asyncResult1.getValue() shouldEqual 21
            asyncResult2.getValue() shouldEqual 23
        }
    }
}