package io.kommons.designpatterns.semaphore

import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.fail

class SemaphoreTest {

    private val semaphore = Semaphore(3)

    @RepeatedTest(10)
    fun `acquire and release`() {

        semaphore.availableLicenses shouldEqualTo 3

        repeat(3) {
            try {
                semaphore.acquire()
                semaphore.availableLicenses shouldEqualTo semaphore.licenses - it - 1
            } catch (e: InterruptedException) {
                fail(e.message)
            }
        }

        repeat(3) {
            semaphore.release()
            semaphore.availableLicenses shouldEqualTo it + 1
        }

        semaphore.release()
        semaphore.availableLicenses shouldEqualTo 3
    }
}