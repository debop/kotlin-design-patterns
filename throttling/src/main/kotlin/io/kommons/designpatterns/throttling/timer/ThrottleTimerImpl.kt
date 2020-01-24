package io.kommons.designpatterns.throttling.timer

import io.kommons.designpatterns.throttling.CallsCount
import java.time.Duration
import java.util.Timer
import java.util.TimerTask

/**
 * Implementation of throttler interface. This class resets the counter every second.
 *
 * @author debop
 */
class ThrottleTimerImpl(private val throttlePeriod: Duration,
                        private val callsCount: CallsCount): Throttler {

    /**
     * A timer is initiated with this method. The timer runs every second and resets the counter.
     */
    override fun start() {
        Timer(true).schedule(timerTask { callsCount.reset() }, 0L, throttlePeriod.toMillis())
    }

    private inline fun timerTask(crossinline block: () -> Unit): TimerTask =
        object: TimerTask() {
            override fun run() {
                block.invoke()
            }
        }
}