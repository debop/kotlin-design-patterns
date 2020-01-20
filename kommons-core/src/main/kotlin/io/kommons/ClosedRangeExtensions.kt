package io.kommons

import java.math.BigDecimal
import java.math.BigInteger
import java.util.Objects

fun <T: Comparable<T>> ClosedRange<T>.contains(other: ClosedRange<T>): Boolean =
    start <= other.start && endInclusive >= other.endInclusive


fun <T: Comparable<T>> Iterable<ClosedRange<T>>.isAscending(): Boolean {
    val first = firstOrNull()
    if (first == null) {
        return true
    }
    var max = first.start
    return drop(1).fold(true) { isAscending, elem ->
        val newAscending = isAscending && (max <= elem.start)
        max = maxOf(max, elem.start)
        newAscending
    }
}

@SinceKotlin("1.1")
operator fun BigDecimal.rangeTo(endInclusive: BigDecimal): ClosedBigNumberRange<BigDecimal> =
    ClosedBigDecimalRange(this, endInclusive)

@SinceKotlin("1.1")
operator fun BigInteger.rangeTo(endInclusive: BigInteger): ClosedBigNumberRange<BigInteger> =
    ClosedBigIntegerRange(this, endInclusive)


interface ClosedBigNumberRange<T: Comparable<T>>: ClosedRange<T> {

    override fun contains(value: T): Boolean = lessThanOrEquals(start, value) && lessThanOrEquals(value, endInclusive)
    override fun isEmpty(): Boolean = !lessThanOrEquals(start, endInclusive)

    /**
     * Compares two values of range domain type and returns true if first is less than or equal to second.
     */
    fun lessThanOrEquals(a: T, b: T): Boolean
}

internal class ClosedBigDecimalRange(override val start: BigDecimal,
                                     override val endInclusive: BigDecimal): ClosedBigNumberRange<BigDecimal> {

    override fun lessThanOrEquals(a: BigDecimal, b: BigDecimal): Boolean = a <= b

    override fun contains(value: BigDecimal): Boolean = value >= start && value <= endInclusive

    override fun isEmpty(): Boolean = start > endInclusive

    override fun equals(other: Any?): Boolean {
        return other is ClosedBigDecimalRange &&
               (isEmpty() && other.isEmpty() || (start == other.start && endInclusive == other.endInclusive))
    }

    override fun hashCode(): Int = Objects.hash(start, endInclusive)

    override fun toString(): String = "$start..$endInclusive"

}

internal class ClosedBigIntegerRange(override val start: BigInteger,
                                     override val endInclusive: BigInteger): ClosedBigNumberRange<BigInteger> {

    override fun lessThanOrEquals(a: BigInteger, b: BigInteger): Boolean = a <= b

    override fun contains(value: BigInteger): Boolean = value >= start && value <= endInclusive

    override fun isEmpty(): Boolean = start > endInclusive

    override fun equals(other: Any?): Boolean {
        return other is ClosedBigIntegerRange &&
               (isEmpty() && other.isEmpty() || (start == other.start && endInclusive == other.endInclusive))
    }

    override fun hashCode(): Int = Objects.hash(start, endInclusive)

    override fun toString(): String = "$start..$endInclusive"

}
    