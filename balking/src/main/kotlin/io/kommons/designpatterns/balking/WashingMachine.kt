package io.kommons.designpatterns.balking

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import io.kommons.logging.error
import io.kommons.logging.info
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.time.Duration

/**
 * WashingMachine
 */
class WashingMachine(private val delayProvider: DelayProvider = defaultDelayProvider) {

    companion object: KLogging() {
        @JvmStatic
        private val defaultDelayProvider = object: DelayProvider {
            override suspend fun executeAfterDelay(delayDuration: Duration, task: suspend () -> Unit) {
                log.debug { "Delay ${delayDuration.toMillis()} msec ... " }
                delay(delayDuration.toMillis())
                log.debug { "Invoke task ..." }
                task.invoke()
            }
        }
    }

    var state = WashingMachineState.ENABLED
        private set

    fun wash() {
        synchronized(this) {
            log.info { "Actual machine state: $state" }
            if (state == WashingMachineState.WASHING) {
                log.error { "ERROR: Cannot wash if the machine has been already washing!" }
                return
            }

            state = WashingMachineState.WASHING
        }
        log.info { "Doing the washing." }

        runBlocking {
            delayProvider.executeAfterDelay(Duration.ofMillis(50)) {
                endOfWashing()
            }
        }
    }

    @Synchronized
    fun endOfWashing() {
        state = WashingMachineState.ENABLED
        log.info { "Washing completed." }
    }
}