package io.kommons.utils.concurrency

import io.kommons.logging.KLogging
import io.kommons.logging.trace
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * ConcurrencyLocksTest
 *
 * @author debop
 */
class ConcurrencyLocksTest {

    companion object: KLogging()

    @Test
    fun `with CountDownLatch`() {

        val x = 1.withLatch {
            log.trace { "with CountDownLatch ..." }
            Thread.sleep(100)
            countDown()
            log.trace { "countDown latch ..." }
            42
        }

        x shouldEqual 42
    }

    @RepeatedTest(10)
    fun `with readLock`() = runBlocking<Unit> {
        var counter = 0

        val rwLock = ReentrantReadWriteLock()

        val result1 = async {
            rwLock.withWriteLock {
                delay(100)
                counter = 42
            }
            rwLock.withReadLock {
                delay(5)
                counter
            }
        }

        val result2 = async {
            rwLock.withWriteLock {
                delay(5)
                counter = 21
            }
            rwLock.withReadLock {
                delay(100)
                counter
            }
        }

        log.trace("result1= ${result1.await()}, result2=${result2.await()}")
        result1.await() shouldEqualTo 42
        result2.await() shouldEqualTo 42
    }
}