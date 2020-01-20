package io.kommons

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class BigDecimalExtensionsTest {

    @Test
    fun `compare BigDecimal`() {
        assertTrue { BigDecimal.ZERO < BigDecimal.ONE }
        assertFalse { BigDecimal.ZERO > BigDecimal.ONE }
    }

    @Test
    fun `basic operators`() {
        val b = 20.toBigDecimal()
        val a = 10.toBigDecimal()

        a + a shouldEqual b
        b - a shouldEqual a
        a * 2 shouldEqual b
        b / 2 shouldEqual a
    }

    @Test
    fun `collection operator of BigDecimal`() {
        val numbers = List(100) { it.toBigDecimal() }

        numbers.sum() shouldEqual (0 until 100).sum().toBigDecimal()
    }
}