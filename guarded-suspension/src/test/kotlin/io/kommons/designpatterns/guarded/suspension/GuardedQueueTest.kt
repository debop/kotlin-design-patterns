package io.kommons.designpatterns.guarded.suspension

import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * GuardedQueueTest
 */
class GuardedQueueTest {

    @Volatile private var value: Int? = null

    @Test
    fun `get queue item`() {
        val queue = GuardedQueue()
        val executor = Executors.newFixedThreadPool(2)
        executor.submit { value = queue.get() }
        executor.submit { queue.put(10) }
        executor.shutdown()
        executor.awaitTermination(30, TimeUnit.SECONDS)

        value shouldEqual 10
    }

    @Test
    fun `put queue item`() {
        val queue = GuardedQueue()
        queue.put(12)
        queue.get() shouldEqualTo 12
    }
}