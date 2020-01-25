package io.kommons.utils.memorizer

import io.kommons.logging.KLogging
import io.kommons.logging.trace

/**
 * InMemoryMemorizerTest
 *
 * @author debop
 */
class InMemoryMemorizerTest: AbstractMemorizerTest() {

    companion object: KLogging()

    override val factorial: FactorialProvider = FactorialProvider()

    override val fibonacci: FibonacciProvider = FibonacciProvider()

    override val heavyFunc: (Int) -> Int = inMemoryMemorizer { x ->
        log.trace { "heavy($x)" }
        Thread.sleep(100)
        x * x
    }
}


open class FactorialProvider {

    protected open val cachedCalc = inMemoryMemorizer<Long, Long> { calc(it) }.apply {
        put(0L, 1L)
        put(1L, 1L)
    }

    fun calc(n: Long): Long {
        return when (n) {
            0L   -> 1
            1L   -> 1
            else -> n * cachedCalc(n - 1)
        }
    }
}

open class FibonacciProvider {

    companion object: KLogging()

    protected open val cachedCalc = inMemoryMemorizer<Long, Long> { calc(it) }.apply {
        put(0, 0L)
        put(1L, 1L)
        put(2L, 1L)
    }

    fun calc(n: Long): Long {
        // log.trace { "fibonacci($n)" }
        return when (n) {
            0L   -> 0L
            1L   -> 1L
            2L   -> 1L
            else -> cachedCalc(n - 1) + cachedCalc(n - 2)
        }
    }
}