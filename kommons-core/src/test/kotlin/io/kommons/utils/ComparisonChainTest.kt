package io.kommons.utils

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test

/**
 * ComparisonChainTest
 *
 * @author debop
 */
class ComparisonChainTest {

    companion object: KLogging()

    @Test
    fun `compare without properties`() {
        ComparisonChain.start().result shouldEqualTo 0
    }

    @Test
    fun `compare with same string and int`() {
        ComparisonChain.start()
            .compare("a", "a")
            .compare(1, 1)
            .compare(1L, 1L)
            .compare(1.0F, 1.0F)
            .compare(1.0, 1.0)
            .result shouldEqualTo 0
    }

    @Test
    fun `compare with different string and int`() {
        ComparisonChain.start()
            .compare("b", "a")
            .compare(1, 1)
            .compare(1L, 1L)
            .result shouldEqualTo 1

        ComparisonChain.start()
            .compare("a", "b")
            .compare(1, 1)
            .compare(1L, 1L)
            .result shouldEqualTo -1
    }

    @Test
    fun `compare true first`() {
        ComparisonChain.start().compareTrueFirst(true, true).result shouldEqualTo 0
        ComparisonChain.start().compareTrueFirst(true, false).result shouldEqualTo -1
        ComparisonChain.start().compareTrueFirst(false, true).result shouldEqualTo 1
        ComparisonChain.start().compareTrueFirst(false, false).result shouldEqualTo 0
    }

    @Test
    fun `compare false first`() {
        ComparisonChain.start().compareFalseFirst(true, true).result shouldEqualTo 0
        ComparisonChain.start().compareFalseFirst(true, false).result shouldEqualTo 1
        ComparisonChain.start().compareFalseFirst(false, true).result shouldEqualTo -1
        ComparisonChain.start().compareFalseFirst(false, false).result shouldEqualTo 0
    }
}