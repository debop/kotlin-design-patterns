package io.kommons.utils.memorizer

import io.kommons.logging.trace
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

class InMemoryAsyncMemorizerTest: AbstractAsyncMemorizerTest() {

    override val factorial: AsyncFactorialProvider = AsyncFactorialProvider()
    override val fibonacci: AsyncFibonacciProvider = AsyncFibonacciProvider()

    override val heavyFunc: (Int) -> CompletionStage<Int> = inMemoryAsyncMemorizer { x ->
        log.trace { "execute heavy($x)" }
        Thread.sleep(1000)
        CompletableFuture.completedFuture(x * x)
    }

}

open class AsyncFactorialProvider {

    protected open val cachedCalc: (Long) -> CompletionStage<Long> = inMemoryAsyncMemorizer { calc(it) }

    fun calc(x: Long): CompletionStage<Long> {
        return when (x) {
            0L   -> CompletableFuture.completedFuture(1L)
            1L   -> CompletableFuture.completedFuture(1L)
            else -> cachedCalc(x - 1).thenApply { it * x }
        }
    }
}

open class AsyncFibonacciProvider {

    protected open val cachedCalc: (Long) -> CompletionStage<Long> = inMemoryAsyncMemorizer { calc(it) }

    fun calc(x: Long): CompletionStage<Long> {
        return when (x) {
            0L   -> CompletableFuture.completedFuture(0L)
            1L   -> CompletableFuture.completedFuture(1L)
            2L   -> CompletableFuture.completedFuture(1L)
            else -> cachedCalc(x - 1).thenComposeAsync { x1 -> cachedCalc(x - 2).thenApply { x2 -> x1 + x2 } }
        }
    }
}