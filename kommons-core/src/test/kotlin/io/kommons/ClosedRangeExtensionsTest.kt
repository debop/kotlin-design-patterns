package io.kommons

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ClosedRangeExtensionsTest {

    companion object: KLogging()

    private fun longRanges(vararg ranges: IntRange): List<LongRange> =
        ranges.map { it.start.toLong()..it.endInclusive.toLong() }


    @Test
    fun `range is ascending`() {
        longRanges(0..10, 1..5, 12..13).isAscending().shouldBeTrue()
        longRanges(2..10, 1..5, 5..13).isAscending().shouldBeFalse()
    }

    @Test
    fun `create closed range by BigDecimal`() {
        val range = 5.toBigDecimal()..10.toBigDecimal()

        assertTrue { 6.toBigDecimal() in range }
        assertFalse { 3.toBigDecimal() in range }

        range.contains(7.toBigDecimal()..10.toBigDecimal()).shouldBeTrue()
        range.contains(7.toBigDecimal()..11.toBigDecimal()).shouldBeFalse()
        range.contains(4.toBigDecimal()..10.toBigDecimal()).shouldBeFalse()
    }

    @Test
    fun `create closed range by BigInteger`() {
        val range = 5.toBigInteger()..10.toBigInteger()

        assertTrue { 6.toBigInteger() in range }
        assertFalse { 3.toBigInteger() in range }

        range.contains(7.toBigInteger()..10.toBigInteger()).shouldBeTrue()
        range.contains(7.toBigInteger()..11.toBigInteger()).shouldBeFalse()
        range.contains(4.toBigInteger()..10.toBigInteger()).shouldBeFalse()
    }
}