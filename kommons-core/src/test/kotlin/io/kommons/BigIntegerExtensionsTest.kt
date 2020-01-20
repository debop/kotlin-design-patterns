package io.kommons

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigInteger

class BigIntegerExtensionsTest {

    @Test
    fun `compare BigDecimal`() {
        assertTrue { BigInteger.ZERO < BigInteger.ONE }
        assertFalse { BigInteger.ZERO > BigInteger.ONE }
    }

    @Test
    fun `basic operators`() {
        val b = 20.toBigInt()
        val a = 10.toBigInt()

        a + a shouldEqual b
        b - a shouldEqual a
        a * 2 shouldEqual b
        b / 2 shouldEqual a
    }

    @Test
    fun `collection operator of BigDecimal`() {
        val numbers = List(100) { it.toBigInt() }

        numbers.sum() shouldEqual (0 until 100).sum().toBigInt()
    }
}