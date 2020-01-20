package io.kommons

import io.kommons.logging.KLogging
import io.kommons.logging.trace
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger

class ConvertExtensionsTest {

    companion object: KLogging()

    @Test
    fun `convert any as char`() {
        val one = 'A'
        val nullValue: Char? = null

        one.asChar() shouldEqual 'A'
        nullValue.asChar() shouldEqual '\u0000'

        "".asChar() shouldEqual '\u0000'
        "C".asChar() shouldEqual 'C'
        "1".asChar() shouldEqual '1'
        "\t".asChar() shouldEqual '\t'

        "5000".asChar() shouldEqual '5'

        log.trace { "5000.asChar() = ${5000.asChar()}" }
        5000.asChar() shouldEqual 5000.asByte().toChar()
    }

    @Test
    fun `convert any as byte`() {
        val one = "1".toByte()
        val nullValue: Byte? = null

        one.asByte() shouldEqual 1.toByte()
        nullValue.asByte() shouldEqual 0.toByte()

        "".asByte() shouldEqual 0.toByte()
        "C".asByte() shouldEqual 0.toByte()
        "1".asByte() shouldEqual 1.toByte()
        "\t".asByte() shouldEqual 0.toByte()

        12.asByte() shouldEqual 12.toByte()
        "12".asByte() shouldEqual 12.toByte()


        "5000".asByte() shouldEqual 5000.toByte()

        log.trace { "5000.asByte() = ${5000.asByte()}" }
        5000.asByte() shouldEqual 5000.toByte()
    }

    @Test
    fun `convert any as short`() {
        val one = "1".toShort()
        val nullValue: Short? = null

        one.asShort() shouldEqual 1.toShort()
        nullValue.asShort() shouldEqual 0.toShort()

        "".asShort() shouldEqual 0.toShort()
        "C".asShort() shouldEqual 0.toShort()
        "1".asShort() shouldEqual 1.toShort()
        "\t".asShort() shouldEqual 0.toShort()

        12.asShort() shouldEqual 12.toShort()
        "12".asShort() shouldEqual 12.toShort()


        "5000".asShort() shouldEqual 5000.toShort()

        log.trace { "5000.asShort() = ${5000.asShort()}" }
        5000.asShort() shouldEqual 5000.toShort()
    }

    @Test
    fun `convert any as Int`() {
        val one = "1"
        val nullValue: Int? = null

        one.asInt() shouldEqual 1
        nullValue.asInt() shouldEqual 0

        "".asInt() shouldEqual 0
        "C".asInt() shouldEqual 0
        "1".asInt() shouldEqual 1
        "\t".asInt() shouldEqual 0

        12.asInt() shouldEqual 12
        "12".asInt() shouldEqual 12

        "5000".asInt() shouldEqual 5000

        log.trace { "5000.asInt() = ${5000.asInt()}" }
        5000.asInt() shouldEqual 5000
    }

    @Test
    fun `convert any as Int or Null`() {
        val one = "1"
        val nullValue: Int? = null

        one.asIntOrNull() shouldEqual 1
        nullValue.asIntOrNull().shouldBeNull()

        "".asIntOrNull().shouldBeNull()
        "C".asIntOrNull().shouldBeNull()
        "1".asIntOrNull() shouldEqual 1
        "\t".asIntOrNull().shouldBeNull()

        12.asIntOrNull() shouldEqual 12
        "12".asIntOrNull() shouldEqual 12

        "5000".asIntOrNull() shouldEqual 5000

        log.trace { "5000.asIntOrNull() = ${5000.asIntOrNull()}" }
        5000.asIntOrNull() shouldEqual 5000
    }

    @Test
    fun `convert any as Long`() {
        val one = "1"
        val nullValue: Long? = null

        one.asLong() shouldEqual 1L
        nullValue.asLong() shouldEqual 0L

        "".asLong() shouldEqual 0L
        "C".asLong() shouldEqual 0L
        "1".asLong() shouldEqual 1L
        "\t".asLong() shouldEqual 0L

        12.asLong() shouldEqual 12L
        "12".asLong() shouldEqual 12L


        "5000".asLong() shouldEqual 5000L

        log.trace { "5000.asLong() = ${5000.asLong()}" }
        5000.asLong() shouldEqual 5000L
    }

    @Test
    fun `convert any as Float`() {
        val one = "1"
        val nullValue: Float? = null

        one.asFloat() shouldEqual 1.0F
        nullValue.asFloat() shouldEqual 0.0F

        "".asFloat() shouldEqual 0.0F
        "C".asFloat() shouldEqual 0.0F
        "1".asFloat() shouldEqual 1.0F
        "\t".asFloat() shouldEqual 0.0F

        12.asFloat() shouldEqual 12.0F
        "12".asFloat() shouldEqual 12.0F


        "5000".asFloat() shouldEqual 5000.0F

        log.trace { "5000.asFloat() = ${5000.asFloat()}" }
        5000.asFloat() shouldEqual 5000.0F
    }

    @Test
    fun `convert any as Double`() {
        val one = "1"
        val nullValue: Double? = null

        one.asDouble() shouldEqual 1.0
        nullValue.asDouble() shouldEqual 0.0

        "".asDouble() shouldEqual 0.0
        "C".asDouble() shouldEqual 0.0
        "1".asDouble() shouldEqual 1.0
        "\t".asDouble() shouldEqual 0.0

        12.asDouble() shouldEqual 12.0
        "12".asDouble() shouldEqual 12.0


        "5000".asDouble() shouldEqual 5000.0

        log.trace { "5000.asDouble() = ${5000.asDouble()}" }
        5000.asDouble() shouldEqual 5000.0
    }

    @Test
    fun `convert any as BigInteger`() {
        val one = "1"
        val nullValue: Byte? = null

        one.asBigInt() shouldEqual BigInteger.ONE
        nullValue.asBigInt() shouldEqual BigInteger.ZERO

        "".asBigInt() shouldEqual BigInteger.ZERO
        "C".asBigInt() shouldEqual BigInteger.ZERO
        "1".asBigInt() shouldEqual BigInteger.ONE
        "\t".asBigInt() shouldEqual BigInteger.ZERO

        12.asBigInt() shouldEqual 12.toBigInteger()
        "12".asBigInt() shouldEqual 12.toBigInteger()


        "5000".asBigInt() shouldEqual 5000.toBigInteger()

        log.trace { "5000.asBigInt() = ${5000.asBigInt()}" }
        5000.asBigInt() shouldEqual 5000.toBigInteger()
    }

    @Test
    fun `convert any as BigDecimal`() {
        val one = "1"
        val nullValue: Byte? = null

        one.asBigDecimal() shouldEqual BigDecimal.ONE
        nullValue.asBigDecimal() shouldEqual BigDecimal.ZERO

        "".asBigDecimal() shouldEqual BigDecimal.ZERO
        "C".asBigDecimal() shouldEqual BigDecimal.ZERO
        "1".asBigDecimal() shouldEqual BigDecimal.ONE
        "\t".asBigDecimal() shouldEqual BigDecimal.ZERO

        12.asBigDecimal() shouldEqual 12.toBigDecimal()
        "12".asBigDecimal() shouldEqual 12.toBigDecimal()


        "5000".asBigDecimal() shouldEqual 5000.toBigDecimal()

        log.trace { "5000.asBigDecimal() = ${5000.asBigDecimal()}" }
        5000.asBigDecimal() shouldEqual 5000.toBigDecimal()
    }

    @Test
    fun `floor float number`() {
        val one = 1.00123456f
        val one1 = 1.011111f
        val one5 = 1.0502345f
        val one49 = 1.0499999999f
        val empty: Float? = null

        one.asFloatFloor(2) shouldEqualTo 1.00F
        one.asFloatFloor(1) shouldEqualTo 1.0F

        one1.asFloatFloor(2) shouldEqualTo 1.01F
        one1.asFloatFloor(1) shouldEqualTo 1.0F

        one5.asFloatFloor(2) shouldEqualTo 1.05F
        one5.asFloatFloor(1) shouldEqualTo 1.0F

        one49.asFloatFloor(2) shouldEqualTo 1.04F
        one49.asFloatFloor(1) shouldEqualTo 1.0F

        empty.asFloatFloor(2) shouldEqualTo 0.00F
        empty.asFloatFloor(1) shouldEqualTo 0.0F
    }

    @Test
    fun `floor double number`() {
        val one = 1.00123456
        val one1 = 1.011111
        val one5 = 1.0512341
        val one49 = 1.0499999999
        val empty: Double? = null

        one.asDoubleFloor(2) shouldEqualTo 1.00
        one.asDoubleFloor(1) shouldEqualTo 1.0

        one1.asDoubleFloor(2) shouldEqualTo 1.01
        one1.asDoubleFloor(1) shouldEqualTo 1.0

        one5.asDoubleFloor(2) shouldEqualTo 1.05
        one5.asDoubleFloor(1) shouldEqualTo 1.0

        one49.asDoubleFloor(2) shouldEqualTo 1.04
        one49.asDoubleFloor(1) shouldEqualTo 1.0

        empty.asDoubleFloor(2) shouldEqualTo 0.00
        empty.asDoubleFloor(1) shouldEqualTo 0.0


        "13567.6".asDoubleFloor(-2) shouldEqualTo 13500.0
    }

    @Test
    fun `round float number`() {
        val one = 1.00123456f
        val one1 = 1.011111f
        val one5 = 1.0502345f
        val one49 = 1.0499999999f
        val empty: Float? = uninitialized()

        one.asFloatRound(2) shouldEqualTo 1.00F
        one.asFloatRound(1) shouldEqualTo 1.0F

        one1.asFloatRound(2) shouldEqualTo 1.01F
        one1.asFloatRound(1) shouldEqualTo 1.0F

        one5.asFloatRound(2) shouldEqualTo 1.05F
        one5.asFloatRound(1) shouldEqualTo 1.1F

        one49.asFloatRound(2) shouldEqualTo 1.05F
        one49.asFloatRound(1) shouldEqualTo 1.1F

        empty.asFloatRound(2) shouldEqualTo 0.00F
        empty.asFloatRound(1) shouldEqualTo 0.0F
    }

    @Test
    fun `round double number`() {
        val one = 1.00123456
        val one1 = 1.011111
        val one5 = 1.0512341
        val one49 = 1.0499999999
        val empty: Double? = uninitialized()

        one.asDoubleRound(2) shouldEqualTo 1.00
        one.asDoubleRound(1) shouldEqualTo 1.0

        one1.asDoubleRound(2) shouldEqualTo 1.01
        one1.asDoubleRound(1) shouldEqualTo 1.0

        one5.asDoubleRound(2) shouldEqualTo 1.05
        one5.asDoubleRound(1) shouldEqualTo 1.1

        one49.asDoubleRound(2) shouldEqualTo 1.05
        one49.asDoubleRound(1) shouldEqualTo 1.0

        empty.asDoubleRound(2) shouldEqualTo 0.00
        empty.asDoubleRound(1) shouldEqualTo 0.0
    }
}