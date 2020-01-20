package io.kommons.utils.memorizer

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertTimeout
import java.time.Duration
import java.util.concurrent.CompletionStage
import kotlin.system.measureTimeMillis

/**
 * AbstractAsyncMemorizerTest
 *
 * @author debop
 */
abstract class AbstractAsyncMemorizerTest {

    companion object: KLogging()

    abstract val factorial: AsyncFactorialProvider
    abstract val fibonacci: AsyncFibonacciProvider

    abstract val heavyFunc: (Int) -> CompletionStage<Int>

    @Test
    fun `run fibonacci async`() {
        val x1 = fibonacci.calc(500).toCompletableFuture().get()

        assertTimeout(Duration.ofMillis(500)) {
            fibonacci.calc(500).toCompletableFuture().get()
        } shouldEqualTo x1
    }

    @Test
    fun `run factorial async`() {
        val x1 = factorial.calc(100).toCompletableFuture().get()

        assertTimeout(Duration.ofMillis(500)) {
            factorial.calc(100).toCompletableFuture().get()
        } shouldEqualTo x1
    }

    @Test
    fun `call heavy async function`() {
        measureTimeMillis {
            heavyFunc(10).toCompletableFuture().get() shouldEqualTo 100
        }

        assertTimeout(Duration.ofMillis(500)) {
            heavyFunc(10).toCompletableFuture().get() shouldEqualTo 100
        }
    }
}

