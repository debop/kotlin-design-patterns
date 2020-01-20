package io.kommons.utils

import io.kommons.compareBoolean
import io.kommons.logging.KLogging

/**
 * A utility for performing a chained comparison statement.
 *
 * For example:
 * ```
 *   fun compareTo(that:Foo): Int {
 *     return ComparisonChain.start()
 *         .compare(this.aString, that.aString)
 *         .compare(this.anInt, that.anInt)
 *         .compare(this.anEnum, that.anEnum, Ordering.natural().nullsLast())
 *         .result
 *   }
 * ```
 *
 * @author debop
 */
abstract class ComparisonChain {

    abstract val result: Int

    abstract fun <T: Comparable<T>> compare(left: T, right: T): ComparisonChain
    abstract fun <T: Any> compare(left: T?, right: T?, comparator: Comparator<T>): ComparisonChain
    abstract fun compare(left: Int, right: Int): ComparisonChain
    abstract fun compare(left: Long, right: Long): ComparisonChain
    abstract fun compare(left: Float, right: Float): ComparisonChain
    abstract fun compare(left: Double, right: Double): ComparisonChain
    abstract fun compareTrueFirst(left: Boolean, right: Boolean): ComparisonChain
    abstract fun compareFalseFirst(left: Boolean, right: Boolean): ComparisonChain

    companion object: KLogging() {

        val ACTIVE: ComparisonChain = object: ComparisonChain() {
            override val result: Int
                get() = 0

            override fun <T: Comparable<T>> compare(left: T, right: T): ComparisonChain =
                classify(left.compareTo(right))

            override fun <T: Any> compare(left: T?, right: T?, comparator: Comparator<T>): ComparisonChain =
                classify(comparator.compare(left, right))

            override fun compare(left: Int, right: Int): ComparisonChain = classify(left.compareTo(right))
            override fun compare(left: Long, right: Long): ComparisonChain = classify(left.compareTo(right))
            override fun compare(left: Float, right: Float): ComparisonChain = classify(left.compareTo(right))
            override fun compare(left: Double, right: Double): ComparisonChain = classify(left.compareTo(right))
            override fun compareTrueFirst(left: Boolean, right: Boolean): ComparisonChain = classify(compareBoolean(right, left))
            override fun compareFalseFirst(left: Boolean, right: Boolean): ComparisonChain = classify(compareBoolean(left, right))
        }

        val LESS: ComparisonChain = InactiveComparisonChain(-1)
        val GREATER: ComparisonChain = InactiveComparisonChain(1)

        private fun classify(result: Int): ComparisonChain = when {
            result < 0 -> LESS
            result > 0 -> GREATER
            else       -> ACTIVE
        }

        fun start(): ComparisonChain = ACTIVE
    }


    private class InactiveComparisonChain(override val result: Int): ComparisonChain() {
        override fun <T: Comparable<T>> compare(left: T, right: T): ComparisonChain = this
        override fun <T: Any> compare(left: T?, right: T?, comparator: Comparator<T>): ComparisonChain = this
        override fun compare(left: Int, right: Int): ComparisonChain = this
        override fun compare(left: Long, right: Long): ComparisonChain = this
        override fun compare(left: Float, right: Float): ComparisonChain = this
        override fun compare(left: Double, right: Double): ComparisonChain = this
        override fun compareTrueFirst(left: Boolean, right: Boolean): ComparisonChain = this
        override fun compareFalseFirst(left: Boolean, right: Boolean): ComparisonChain = this
    }

}