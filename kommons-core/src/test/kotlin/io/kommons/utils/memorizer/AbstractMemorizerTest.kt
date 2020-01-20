package io.kommons.utils.memorizer

import org.amshove.kluent.shouldBeGreaterOrEqualTo
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertTimeout
import java.time.Duration
import kotlin.system.measureTimeMillis

abstract class AbstractMemorizerTest {

    abstract val factorial: FactorialProvider
    abstract val fibonacci: FibonacciProvider
    abstract val heavyFunc: (Int) -> Int

    @Test
    fun `run fibonacci`() {
        val x1 = fibonacci.calc(500)

        assertTimeout(Duration.ofMillis(500)) {
            fibonacci.calc(500)
        } shouldEqualTo x1
    }

    @Test
    fun `run factorial`() {
        val f1 = factorial.calc(100)

        assertTimeout(Duration.ofMillis(500)) {
            factorial.calc(100)
        } shouldEqualTo f1
    }

    @Test
    fun `call heavy function`() {
        measureTimeMillis {
            heavyFunc(10) shouldEqualTo 100
        } shouldBeGreaterOrEqualTo 100

        assertTimeout(Duration.ofMillis(500)) {
            heavyFunc(10) shouldEqualTo 100
        }
    }
}