package io.kommons.utils

import io.kommons.assertInRange
import io.kommons.assertPositiveNumber
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater

/**
 * [java.util.concurrent.atomic.AtomicInteger] 처럼 Int 값을 atomic 하게 증가시키지만, 최대값에 도달하면 0 으로 reset 한다.
 *
 * @author debop
 */
class AtomicIntRoundrobin(val maximum: Int) {

    init {
        maximum.assertPositiveNumber("maximum")
    }

    @Volatile
    private var currentValue: Int = 0

    private val updater =
        AtomicIntegerFieldUpdater.newUpdater(AtomicIntRoundrobin::class.java, "currentValue")

    fun get(): Int = currentValue

    fun set(value: Int) {
        value.assertInRange(0, maximum - 1, "value")
        updater.set(this, value)
    }

    fun next(): Int {
        if (maximum <= 1) {
            return 0
        }
        while (true) {
            val current = get()
            val next = if (current == maximum - 1) 0 else current + 1
            if (updater.compareAndSet(this, current, next)) {
                return next
            }
        }
    }
}