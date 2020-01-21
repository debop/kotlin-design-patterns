package io.kommons.designpatterns.throttling.timer

/**
 * An interface for defining the structure of different types of throttling ways.
 */
@FunctionalInterface
interface Throttler {

    /**
     * A timer is initiated with this method. The timer runs every second and resets the counter.
     */
    fun start()

}

fun throttller(block: () -> Unit): Throttler =
    object: Throttler {
        override fun start() {
            block.invoke()
        }
    }