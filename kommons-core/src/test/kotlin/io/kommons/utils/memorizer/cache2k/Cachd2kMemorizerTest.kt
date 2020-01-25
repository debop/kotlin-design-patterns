package io.kommons.utils.memorizer.cache2k

import io.kommons.cache.Cache2kProvider
import io.kommons.logging.KLogging
import io.kommons.utils.memorizer.AbstractMemorizerTest
import io.kommons.utils.memorizer.FactorialProvider
import io.kommons.utils.memorizer.FibonacciProvider

class Cachd2kMemorizerTest: AbstractMemorizerTest() {

    companion object: KLogging()

    private val cache = Cache2kProvider.getCache<Int, Int>("heavyFunc")

    override val factorial: FactorialProvider = Cache2kFactorialProvider()

    override val fibonacci: FibonacciProvider = Cache2kFibonacciProvider()

    override val heavyFunc: (Int) -> Int = cache.memorizer { x ->
        Thread.sleep(100)
        x * x
    }

}

class Cache2kFactorialProvider: FactorialProvider() {

    private val cache = Cache2kProvider.getCache<Long, Long>("factorial")
    override val cachedCalc = cache.memorizer { calc(it) }

}

class Cache2kFibonacciProvider: FibonacciProvider() {

    private val cache = Cache2kProvider.getCache<Long, Long>("fibonacci")
    override val cachedCalc = cache.memorizer { calc(it) }

}