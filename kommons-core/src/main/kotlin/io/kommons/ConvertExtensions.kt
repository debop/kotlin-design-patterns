package io.kommons

import java.math.BigDecimal
import java.math.BigInteger
import java.nio.charset.Charset
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.roundToLong

/**
 * 객체를 Double 수형으로 변환합니다.
 *
 * @receiver Any?
 * @param defaultValue 변환 실패 시 대체 값
 */
@JvmOverloads
fun Any?.asDouble(defaultValue: Double = 0.0): Double = runCatching {
    when (this) {
        null      -> defaultValue
        is Double -> this
        is Number -> this.toDouble()
        else      -> this.toString().parseNumber()
    }
}.getOrDefault(defaultValue)

fun Any?.asDoubleOrNull(): Double? =
    runCatching { this?.toString()?.toDoubleOrNull() }.getOrNull()

/**
 * 객체를 Float 수형으로 변환합니다.
 *
 * @receiver Any?
 * @param defaultValue 변환 실패 시 대체 값
 */
@JvmOverloads
fun Any?.asFloat(defaultValue: Float = 0.0F): Float = runCatching {
    when (this) {
        null      -> defaultValue
        is Float  -> this
        is Number -> this.toFloat()
        else      -> this.asDouble(defaultValue.toDouble()).toFloat()
    }
}.getOrDefault(defaultValue)

fun Any?.asFloatOrNull(): Float? = runCatching {
    this?.toString()?.toFloatOrNull()
}.getOrNull()

/**
 * 객체를 Long 수형으로 변환합니다.
 *
 * @receiver Any?
 * @param defaultValue 변환 실패 시 대체 값
 */
@JvmOverloads
fun Any?.asLong(defaultValue: Long = 0L): Long = runCatching {
    when (this) {
        null      -> defaultValue
        is Long   -> this
        is Number -> this.toLong()
        else      -> this.asDouble(defaultValue.toDouble()).toLong()
    }
}.getOrDefault(defaultValue)

fun Any?.asLongOrNull(): Long? = runCatching {
    this?.toString()?.toLongOrNull()
}.getOrNull()

/**
 * 객체를 Int 수형으로 변환합니다.
 *
 * @receiver Any?
 * @param defaultValue 변환 실패 시 대체 값
 */
@JvmOverloads
fun Any?.asInt(defaultValue: Int = 0): Int = runCatching {
    when (this) {
        null      -> defaultValue
        is Int    -> this
        is Number -> this.toInt()
        else      -> this.asDouble(defaultValue.toDouble()).toInt()
    }
}.getOrDefault(defaultValue)

fun Any?.asIntOrNull(): Int? = runCatching {
    this?.toString()?.toIntOrNull()
}.getOrNull()

/**
 * 객체를 Short 수형으로 변환합니다.
 *
 * @receiver Any?
 * @param defaultValue 변환 실패 시 대체 값
 */
@JvmOverloads
fun Any?.asShort(defaultValue: Short = 0): Short = runCatching {
    when (this) {
        null      -> defaultValue
        is Short  -> this
        is Number -> this.toShort()
        else      -> this.asDouble(defaultValue.toDouble()).toShort()
    }
}.getOrDefault(defaultValue)

fun Any?.asShortOrNull(): Short? = runCatching {
    this?.toString()?.toShortOrNull()
}.getOrNull()

/**
 * 객체를 Byte 수형으로 변환합니다.
 *
 * @receiver Any?
 * @param defaultValue 변환 실패 시 대체 값
 */
@JvmOverloads
fun Any?.asByte(defaultValue: Byte = 0.toByte()): Byte = runCatching {
    when (this) {
        null      -> defaultValue
        is Char   -> this.toByte()
        is Byte   -> this
        is Number -> this.toByte()
        else      -> this.asDouble(defaultValue.toDouble()).toByte()
    }
}.getOrDefault(defaultValue)

fun Any?.asByteOrNull(): Byte? = runCatching {
    this?.toString()?.toByteOrNull()
}.getOrNull()


/**
 * 객체를 Char 수형으로 변환합니다.
 *
 * @receiver Any?
 * @param defaultValue 변환 실패 시 대체 값
 */
@JvmOverloads
fun Any?.asChar(defaultValue: Char = 0.toChar()): Char = runCatching {
    when (this) {
        null            -> defaultValue
        is Char         -> this
        is CharSequence -> if (this.length > 0) first() else defaultValue
        else            -> asByte(defaultValue.toByte()).toChar()
    }
}.getOrDefault(defaultValue)

fun Any?.asCharOrNull(): Char? = runCatching {
    this?.toString()?.toCharArray()?.firstOrNull()
}.getOrNull()

/**
 * 객체를 BigDecimal 수형으로 변환합니다.
 *
 * @receiver Any?
 * @param defaultValue 변환 실패 시 대체 값
 */
@JvmOverloads
fun Any?.asBigDecimal(defaultValue: BigDecimal = BigDecimal.ZERO): BigDecimal = runCatching {
    when (this) {
        null          -> defaultValue
        is BigDecimal -> this
        is Number     -> this.toBigDecimal()
        else          -> BigDecimal(toString())
    }
}.getOrDefault(defaultValue)

fun Any?.asBigDecimalOrNull(): BigDecimal? = runCatching {
    this?.toString()?.toBigDecimalOrNull()
}.getOrNull()

/**
 * 객체를 BigInteger 수형으로 변환합니다.
 *
 * @receiver Any?
 * @param defaultValue 변환 실패 시 대체 값
 */
@JvmOverloads
fun Any?.asBigInt(defaultValue: BigInteger = BigInteger.ZERO): BigInteger = runCatching {
    when (this) {
        null          -> defaultValue
        is BigInteger -> this
        is Number     -> this.toBigInt()
        else          -> BigInteger(toString())
    }
}.getOrDefault(defaultValue)

fun Any?.asBigIntOrNull(): BigInteger? = runCatching {
    this?.toString()?.toBigIntegerOrNull()
}.getOrNull()

/**
 * 객체를 String 수형으로 변환합니다.
 *
 * @receiver Any?
 * @param defaultValue 변환 실패 시 대체 값
 */
@JvmOverloads
fun Any?.asString(defaultValue: String = ""): String =
    this?.toString() ?: defaultValue

fun Any?.asStringOrNull(): String? = this?.toString()


internal val SIMPLE_DATE_FORMAT = SimpleDateFormat()

/**
 * 객체를 Date 수형으로 변환합니다.
 *
 * @receiver Any?
 * @param defaultValue 변환 실패 시 대체 값
 */
@JvmOverloads
fun Any?.asDate(defaultValue: Date = Date(0L)): Date = runCatching {
    when (this) {
        null         -> defaultValue
        is Number    -> Date(this.toLong())
        is Date      -> this
        is Timestamp -> this
        else         -> SIMPLE_DATE_FORMAT.parse(asString())
    }
}.getOrDefault(defaultValue)

fun Any?.asDateOrNull(): Date? =
    this?.run {
        runCatching { SIMPLE_DATE_FORMAT.parse(this.toString()) }.getOrNull()
    }

@JvmOverloads
fun Any?.asByteArray(charset: Charset = Charsets.UTF_8, defaultValue: ByteArray = emptyByteArray): ByteArray = runCatching {
    when (this) {
        null         -> defaultValue
        is ByteArray -> this
        else         -> toString().toByteArray(charset)
    }
}.getOrDefault(emptyByteArray)

@JvmOverloads
fun Any?.asByteArrayOrNull(charset: Charset = Charsets.UTF_8): ByteArray? = runCatching {
    this?.toString()?.toByteArray(charset)
}.getOrNull()

//
// Floor, Round for specific decimal point
//

//private val decimalFormats = ConcurrentHashMap<Int, DecimalFormat>()
//
//private fun getDecimalFormat(decimalCount: Int): DecimalFormat =
//    decimalFormats.computeIfAbsent(decimalCount) { dc ->
//        if (dc > 0) DecimalFormat("." + "#".repeat(dc))
//        else DecimalFormat("#")
//    }

/**
 * 객체를 [Float]로 변환하면서 [decimalCount] 자릿수에서 내림을 수행합니다.
 *
 * @param decimalCount 자릿 수
 */
@JvmOverloads
fun Any?.asFloatFloor(decimalCount: Int = 0): Float {
    if (decimalCount == 0) {
        return this.asLong().toFloat()
    }

    val decimal = 10.0.pow(decimalCount).toFloat()
    return floor(asFloat() * decimal) / decimal
}

@JvmOverloads
fun Any?.asFloatFloorOrNull(decimalCount: Int = 0): Float? = runCatching {
    this?.asFloatFloor(decimalCount)
}.getOrNull()

/**
 * 객체를 [Float]로 변환하면서 [decimalCount] 자릿수에서 반올림을 수행합니다.
 *
 * @param decimalCount 자릿 수
 */
@JvmOverloads
fun Any?.asFloatRound(decimalCount: Int = 0): Float {
    if (decimalCount == 0) {
        return this.asLong().toFloat()
    }
    val decimal = 10.0.pow(decimalCount).toFloat()
    return (this.asFloat() * decimal).roundToLong() / decimal
}

@JvmOverloads
fun Any?.asFloatRoundOrNull(decimalCount: Int = 0): Float? = runCatching {
    this?.asFloatRound(decimalCount)
}.getOrNull()

/**
 * 객체를 [Float]로 변환하면서 [decimalCount] 자릿수에서 올림을 수행합니다.
 *
 * @param decimalCount 자릿 수
 */
@JvmOverloads
fun Any?.asFloatCeil(decimalCount: Int = 0): Float {
    if (decimalCount == 0) {
        return this.asLong().toFloat()
    }

    val decimal = 10.0.pow(decimalCount).toFloat()
    return ceil(asFloat() * decimal) / decimal
}

@JvmOverloads
fun Any?.asFloatCeilOrNull(decimalCount: Int = 0): Float? = runCatching {
    this?.asFloatCeil(decimalCount)
}.getOrNull()

/**
 * 객체를 [Double]로 변환하면서 [decimalCount] 자릿수에서 내림을 수행합니다.
 *
 * ```kotlin
 * 1.00123456.asDoubleFloor(2) shouldEqualTo 1.00
 * 1.00123456.asDoubleFloor(1) shouldEqualTo 1.0
 * "13567.6".asDoubleFloor(-2) shouldEqualTo 13500.0
 * ```
 *
 * @param decimalCount 자릿 수
 */
@JvmOverloads
fun Any?.asDoubleFloor(decimalCount: Int = 0): Double {
    if (decimalCount == 0) {
        return this.asLong().toDouble()
    }

    val decimal = 10.0.pow(decimalCount)
    return floor(asDouble() * decimal) / decimal
}

@JvmOverloads
fun Any?.asDoubleFloorOrNull(decimalCount: Int = 0): Double? = runCatching {
    this?.asDoubleFloor(decimalCount)
}.getOrNull()

/**
 * 객체를 [Double]로 변환하면서 [decimalCount] 자릿수에서 반올림을 수행합니다.
 *
 * @param decimalCount 자릿 수
 */
@JvmOverloads
fun Any?.asDoubleRound(decimalCount: Int = 0): Double {
    if (decimalCount == 0) {
        return this.asLong().toDouble()
    }
    val decimal = 10.0.pow(decimalCount)
    return (asDouble() * decimal).roundToLong() / decimal
}

@JvmOverloads
fun Any?.asDoubleRoundOrNull(decimalCount: Int = 0): Double? = runCatching {
    this?.asDoubleRound(decimalCount)
}.getOrNull()

/**
 * 객체를 [Double]로 변환하면서 [decimalCount] 자릿수에서 올림을 수행합니다.
 *
 * ```kotlin
 * 1.00123456.asDoubleFloor(2) shouldEqualTo 1.00
 * 1.00123456.asDoubleFloor(1) shouldEqualTo 1.0
 * "13567.6".asDoubleFloor(-2) shouldEqualTo 13600.0
 * ```
 *
 * @param decimalCount 자릿 수
 */
@JvmOverloads
fun Any?.asDoubleCeil(decimalCount: Int = 0): Double {
    if (decimalCount == 0) {
        return this.asLong().toDouble()
    }

    val decimal = 10.0.pow(decimalCount)
    return ceil(asDouble() * decimal) / decimal
}

@JvmOverloads
fun Any?.asDoubleCeilOrNull(decimalCount: Int = 0): Double? = runCatching {
    this?.asDoubleCeil(decimalCount)
}.getOrNull()