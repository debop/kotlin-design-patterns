package io.kommons.designpatterns.singleton

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeLessThan
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis
import kotlin.test.assertTrue

/**
 * This class provides several test case that test singleton construction.
 *
 * The first proves that multiple calls to the singleton getInstance object are the same when called
 * in the SAME thread. The second proves that multiple calls to the singleton getInstance object are
 * the same when called in the DIFFERENT thread.
 *
 * Date: 12/29/15 - 19:25 PM
 * @param <S> Supplier method generating singletons
 * @author Jeroen Meulemeester
 * @author Richard Jones
 */
abstract class AbstractSingletonTest<S>(private val singletonInstanceMethod: () -> S) {

    @Test
    fun `multiple calls return the same object in same thread`() {
        val instance1 = singletonInstanceMethod.invoke()
        val instance2 = singletonInstanceMethod.invoke()
        val instance3 = singletonInstanceMethod.invoke()

        assertTrue { instance2 === instance1 }
        assertTrue { instance3 === instance2 }
        assertTrue { instance1 === instance3 }
    }

    @Test
    fun `multiple calls return the same object in different threads`() = runBlocking<Unit> {

        measureTimeMillis {

            val tasks = List(10_000) {
                GlobalScope.async(Dispatchers.Default) {
                    singletonInstanceMethod.invoke()
                }
            }

            val expectedInstance = singletonInstanceMethod.invoke()
            tasks.awaitAll().all { it === expectedInstance }.shouldBeTrue()

        } shouldBeLessThan 10_000L
    }

}