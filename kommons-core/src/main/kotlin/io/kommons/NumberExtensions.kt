package io.kommons

import java.math.BigDecimal
import java.math.BigInteger
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import kotlin.reflect.KClass

@JvmField
val BigIntMin = Long.MIN_VALUE.toBigInt()
@JvmField
val BigIntMax = Long.MAX_VALUE.toBigInt()

@JvmField
val StandardNumberTypes: HashSet<KClass<out Number>> =
    hashSetOf(Byte::class, Short::class, Int::class, Long::class, BigInteger::class, Float::class, Double::class, BigDecimal::class)

@JvmField
val DefaultDecimalFormat: DecimalFormat = DecimalFormat("#,##0.#")

/**
 * 숫자를 인간이 보기 편하도록 해준다
 */
fun Number.toHuman(): String = DefaultDecimalFormat.format(this)

fun Number.toHuman(df: DecimalFormat): String = df.format(this)

fun <T> T.coerce(minValue: T, maxValue: T): T where T: Number, T: Comparable<T> =
    minOf(maxOf(this, minValue), maxValue)

/**
 * 문자열이 16진법의 숫자를 나타내는지 여부
 *
 * ```kotlin
 * "0xAD".isHexNumber()  // return true
 * "#FF".isHexNumber()  // return true
 * "GG".isHexNumber() // return false
 * ```
 * @receiver 검사할 문자열
 * @return Boolean 문자열이 16진법의 숫자를 표현한 것이면 true
 */
fun String.isHexNumber(): Boolean {
    val trimmed = this.trim()
    val index = if (trimmed.startsWith("-")) 1 else 0

    return trimmed.startsWith("0x", index) || trimmed.startsWith("0X", index) || trimmed.startsWith("#", index)
}

/**
 * 문자열을 [BigInteger]로 파싱합니다.
 * @receiver String
 * @return BigInteger
 */
fun String.decodeBigInt(): BigInteger {

    if (isBlank()) {
        return BigInteger.ZERO
    }

    var radix = 10
    var index = 0
    var negative = false

    // Handle minus sign, if present.
    if (this.startsWith("-")) {
        negative = true
        index++
    }

    // Handle radix specifier, if present.
    if (this.startsWith("0x", index) || this.startsWith("0X", index)) {
        index += 2
        radix = 16
    } else if (this.startsWith("#", index)) {
        index++
        radix = 16
    } else if (this.startsWith("0", index) && this.length > 1 + index) {
        index++
        radix = 8
    }

    val result = BigInteger(this.substring(index), radix)
    return if (negative) result.negate() else result
}

fun String.decodeBigDecimal(): BigDecimal {
    return if (this.isBlank()) BigDecimal.ZERO
    else BigDecimal(this)
}

inline fun <reified T: Number> String.parseNumber(): T {
    val trimmed = this.trim()

    return when (T::class) {
        Byte::class       -> (if (trimmed.isHexNumber()) java.lang.Byte.decode(trimmed) else trimmed.toByte()) as T
        Short::class      -> (if (trimmed.isHexNumber()) java.lang.Short.decode(trimmed) else trimmed.toShort()) as T
        Int::class        -> (if (trimmed.isHexNumber()) java.lang.Integer.decode(trimmed) else trimmed.toInt()) as T
        Long::class       -> (if (trimmed.isHexNumber()) java.lang.Long.decode(trimmed) else trimmed.toLong()) as T
        BigInteger::class -> (if (trimmed.isHexNumber()) trimmed.decodeBigInt() else BigInteger(trimmed)) as T
        Float::class      -> trimmed.toFloat() as T
        Double::class     -> trimmed.toDouble() as T
        BigDecimal::class -> (if (trimmed.isHexNumber()) trimmed.decodeBigDecimal() else BigDecimal(trimmed)) as T
        Number::class     -> trimmed.toDouble() as T
        else              -> error("Cannot convert CharSequence[$this] to target class [${T::class.simpleName}")
    }
}

inline fun <reified T: Number> String.parseNumber(numberFormat: NumberFormat): T =
    parseNumber(T::class, numberFormat)

fun <T: Number> String.parseNumber(targetClass: KClass<T>, numberFormat: NumberFormat): T {
    var decimalFormat: DecimalFormat? = null
    var resetBitDecimal = false

    if (numberFormat is DecimalFormat) {
        decimalFormat = numberFormat
        if (BigDecimal::class == targetClass && !decimalFormat.isParseBigDecimal) {
            decimalFormat.isParseBigDecimal = true
            resetBitDecimal = true
        }
    }
    try {
        val number = numberFormat.parse(this.trim())
        return number.toTargetClass(targetClass)
    } catch (e: ParseException) {
        throw IllegalArgumentException("Could not parse number: " + e.message)
    } finally {
        if (resetBitDecimal) {
            decimalFormat?.isParseBigDecimal = false
        }
    }
}

inline fun <reified T: Number> Number.toTargetClass(): T = toTargetClass(T::class)

@Suppress("UNCHECKED_CAST")
fun <T: Number> Number.toTargetClass(targetClass: KClass<T>): T {
    if (targetClass.isInstance(this))
        return this as T

    return when (targetClass) {

        Byte::class       -> {
            val value = checkedLongValue(targetClass)
            if (value !in Byte.MIN_VALUE..Byte.MAX_VALUE) {
                raiseOverflowException(this, targetClass)
            }
            value.toByte() as T
        }

        Short::class      -> {
            val value = checkedLongValue(targetClass)
            if (value !in Short.MIN_VALUE..Short.MAX_VALUE) {
                raiseOverflowException(this, targetClass)
            }
            value.toShort() as T
        }

        Int::class        -> {
            val value = checkedLongValue(targetClass)
            if (value !in Int.MIN_VALUE..Int.MAX_VALUE) {
                raiseOverflowException(this, targetClass)
            }
            value.toInt() as T
        }

        Long::class       -> {
            val value = checkedLongValue(targetClass)
            value as T
        }

        BigInteger::class -> ((this as? BigDecimal)?.toBigInteger() ?: toLong().toBigInt()) as T

        Float::class      -> this.toFloat() as T

        Double::class     -> this.toDouble() as T

        BigDecimal::class -> this.toBigDecimal() as T

        else              ->
            throw IllegalArgumentException("Could not convert number[$this] of type[${this::class.java.simpleName}] " +
                                           "to unsupported target class [${targetClass.simpleName}]")
    }
}

private fun Number.checkedLongValue(targetClass: KClass<out Number>): Long {
    val bigInt: BigInteger? = when (this) {
        is BigInteger -> this
        is BigDecimal -> this.toBigInteger()
        else          -> null
    }
    bigInt?.let {
        if (it !in BigIntMin..BigIntMax) {
            raiseOverflowException(this, targetClass)
        }
    }
    return toLong()
}

private fun raiseOverflowException(number: Number, targetClass: KClass<*>) {
    throw IllegalArgumentException("Could not convert number[$number] of type [${number::class.java.simpleName}] " +
                                   "to target class [${targetClass.qualifiedName}]: overflow")
}
