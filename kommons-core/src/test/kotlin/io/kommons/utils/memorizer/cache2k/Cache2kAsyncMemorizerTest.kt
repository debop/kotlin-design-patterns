package io.kommons.utils.memorizer.cache2k

import io.kommons.cache.Cache2kProvider
import io.kommons.logging.KLogging
import io.kommons.utils.memorizer.AbstractAsyncMemorizerTest
import io.kommons.utils.memorizer.AsyncFactorialProvider
import io.kommons.utils.memorizer.AsyncFibonacciProvider
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

/**
 * Cache2kAsyncMemorizerTest
 *
 * @author debop
 */
class Cache2kAsyncMemorizerTest: AbstractAsyncMemorizerTest() {

    companion object: KLogging()

    private val cache = Cache2kProvider.getCache<Int, Int>("asyncHeavyFunc")

    override val factorial: AsyncFactorialProvider = Cache2kAsyncFactorialProvider()

    override val fibonacci: AsyncFibonacciProvider = Cache2kAsyncFibonacciProvider()

    override val heavyFunc: (Int) -> CompletionStage<Int> = cache.asyncMemorizer { x ->
        Thread.sleep(1000)
        CompletableFuture.completedFuture(x * x)
    }

}

class Cache2kAsyncFactorialProvider: AsyncFactorialProvider() {

    private val cache = Cache2kProvider.getCache<Long, Long>("asyncFactorial")

    override val cachedCalc = cache.asyncMemorizer { calc(it) }

}

class Cache2kAsyncFibonacciProvider: AsyncFibonacciProvider() {

    private val cache = Cache2kProvider.getCache<Long, Long>("asyncFibonacci")

    override val cachedCalc = cache.asyncMemorizer { calc(it) }

}