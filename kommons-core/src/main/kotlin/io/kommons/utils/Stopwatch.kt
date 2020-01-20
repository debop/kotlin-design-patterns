package io.kommons.utils

import io.kommons.logging.KLogging
import java.util.Locale
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.DAYS
import java.util.concurrent.TimeUnit.HOURS
import java.util.concurrent.TimeUnit.MICROSECONDS
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.MINUTES
import java.util.concurrent.TimeUnit.NANOSECONDS
import java.util.concurrent.TimeUnit.SECONDS

/**
 *  함수 실행 시간을 측정하기 위한 Stopwatch 기능을 제공합니다
 *
 * @author debop
 */
class Stopwatch {
    companion object: KLogging() {
        @JvmStatic
        fun createUnstarted(): Stopwatch = Stopwatch()

        @JvmStatic
        fun createStarted(): Stopwatch = Stopwatch().start()

        @JvmStatic
        private fun chooseUnit(nanos: Long) = when {
            DAYS.convert(nanos, NANOSECONDS) > 0         -> DAYS
            HOURS.convert(nanos, NANOSECONDS) > 0        -> HOURS
            MINUTES.convert(nanos, NANOSECONDS) > 0      -> MINUTES
            SECONDS.convert(nanos, NANOSECONDS) > 0      -> SECONDS
            MILLISECONDS.convert(nanos, NANOSECONDS) > 0 -> MILLISECONDS
            MICROSECONDS.convert(nanos, NANOSECONDS) > 0 -> MICROSECONDS
            else                                         -> NANOSECONDS
        }

        @JvmStatic
        private fun abbreviate(unit: TimeUnit): String = when (unit) {
            NANOSECONDS  -> "ns"
            MICROSECONDS -> "\u03bcs" // μs
            MILLISECONDS -> "ms"
            SECONDS      -> "s"
            MINUTES      -> "min"
            HOURS        -> "h"
            DAYS         -> "d"
            else         -> error("Not supported TimeUnit. unit=$unit")
        }
    }

    private var isRunning: Boolean = false
    private var elapsedNanos: Long = 0L
    private var startTick: Long = 0L

    fun start(): Stopwatch = apply {
        check(!isRunning) { "This stopwatch is already running." }
        isRunning = true
        startTick = System.nanoTime()
    }

    fun stop(): Stopwatch = apply {
        check(isRunning) { "This stopwatch is already stopped." }
        isRunning = false
        elapsedNanos += System.nanoTime() - startTick
    }

    fun reset(): Stopwatch = apply {
        elapsedNanos = 0L
        isRunning = false
    }

    private fun calcElapsedNanos(): Long =
        if (isRunning) System.nanoTime() - startTick + elapsedNanos else elapsedNanos

    fun elapsed(desiredUnit: TimeUnit): Long = desiredUnit.convert(calcElapsedNanos(), NANOSECONDS)

    override fun toString(): String {
        val nanos = calcElapsedNanos()
        val unit = chooseUnit(nanos)

        val value = nanos.toDouble() / NANOSECONDS.convert(1, unit)

        return "%.4g %s".format(Locale.ROOT, value, abbreviate(unit))
    }
}