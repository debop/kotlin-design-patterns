package io.kommons

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.math.BigInteger

class NumberExtensionsTest {

    @Test
    fun `coerceIn operator`() {

        32.coerce(38, 42) shouldEqualTo 38
        32.coerceIn(38..42) shouldEqualTo 38

        44.coerce(38, 42) shouldEqualTo 42
        44.coerceIn(38..42) shouldEqualTo 42

        40.coerce(38, 42) shouldEqualTo 40
        40.coerceIn(38..42) shouldEqualTo 40
    }

    @Test
    fun `string is hex number`() {
        "0x74".isHexNumber().shouldBeTrue()
        "0XHH".isHexNumber().shouldBeTrue()
        "#3A".isHexNumber().shouldBeTrue()
        "-0xAD".isHexNumber().shouldBeTrue()

        "X0FF".isHexNumber().shouldBeFalse()
    }

    @Test
    fun `decode string to BigInteger`() {
        "".decodeBigInt() shouldEqual BigInteger.ZERO
        "-1".decodeBigInt() shouldEqual BigInteger.ONE.negate()
        "#42".decodeBigInt() shouldEqual BigInteger.valueOf(0x42L)
        "0x42".decodeBigInt() shouldEqual BigInteger.valueOf(0x42L)
    }

    @Test
    fun `decode string to BigDecimal`() {
        "".decodeBigDecimal() shouldEqual BigDecimal.ZERO
        "-1".decodeBigDecimal() shouldEqual BigDecimal.ONE.negate()

        assertThrows<NumberFormatException> {
            "#42".decodeBigDecimal() shouldEqual BigDecimal.valueOf(0x42L)
        }
        assertThrows<NumberFormatException> {
            "0x42".decodeBigDecimal() shouldEqual BigDecimal.valueOf(0x42L)
        }
    }

    @Test
    fun `parse string to number`() {
        "0x42".parseNumber<Int>() shouldEqualTo 0x42
        "-0x42".parseNumber<Int>() shouldEqualTo -0x42

        " 42 ".parseNumber<Int>() shouldEqualTo 42
        " 42 ".parseNumber<Long>() shouldEqualTo 42L

        "42".parseNumber<BigInteger>() shouldEqual 42.toBigInt()

        "42.4".parseNumber<Float>() shouldEqualTo 42.4F
        "42.4".parseNumber<Double>() shouldEqualTo 42.4

        "42.4".parseNumber<BigDecimal>() shouldEqualTo 42.4.toBigDecimal()
    }
}