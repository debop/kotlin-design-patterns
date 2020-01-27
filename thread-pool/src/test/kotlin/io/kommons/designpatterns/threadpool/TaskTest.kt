package io.kommons.designpatterns.threadpool

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertTimeout
import java.time.Duration
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

abstract class TaskTest<T: Task>(
    private val factory: (Int) -> T,
    private val expectatedExecutionTime: Int
) {

    companion object: KLogging() {
        private val TASK_COUNT: Int = 128 * 1024
        private val THREAD_COUNT: Int = 8

        private fun <R> get(future: Future<R>): R? =
            runCatching { future.get() }.getOrNull()
    }

    @Test
    fun `id generator test`() {
        assertTimeout(Duration.ofMillis(10000)) {
            val service = Executors.newFixedThreadPool(THREAD_COUNT)

            val tasks = List(TASK_COUNT) { Callable { factory.invoke(1).id } }
            val ids = service.invokeAll(tasks).mapNotNull { get(it) }

            service.shutdown()

            val uniqueIdCount = ids.distinct().count()

            ids.size shouldEqualTo TASK_COUNT
            uniqueIdCount shouldEqualTo TASK_COUNT
        }
    }

    @Test
    fun `get timeMs`() {
        repeat(10) {
            factory.invoke(it).timeMs shouldEqualTo this.expectatedExecutionTime * it
        }
    }

    @Test
    fun `toString should not be empty`() {
        this.factory.invoke(0).toString().shouldNotBeEmpty()
    }
}