package io.kommons.designpatterns.facade

import io.kommons.designpatterns.facade.DwarvenMineWorker.Action.GO_HOME
import io.kommons.designpatterns.facade.DwarvenMineWorker.Action.GO_TO_MINE
import io.kommons.designpatterns.facade.DwarvenMineWorker.Action.GO_TO_SLEEP
import io.kommons.designpatterns.facade.DwarvenMineWorker.Action.WAKE_UP
import io.kommons.designpatterns.facade.DwarvenMineWorker.Action.WORK
import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * DwarvenMineWorker is one of the goldmine subsystems.
 */
interface DwarvenMineWorker {

    companion object: KLogging()

    val name: String

    @JvmDefault
    fun goToSleep() {
        log.info { "$name goes to sleep." }
    }

    @JvmDefault
    fun wakeUp() {
        log.info { "$name wakes up." }
    }

    @JvmDefault
    fun goHome() {
        log.info { "$name goes home." }
    }

    @JvmDefault
    fun goToMine() {
        log.info { "$name goes to the mine." }
    }

    @JvmDefault
    fun action(vararg actions: Action) {
        actions.forEach { action(it) }
    }

    fun work()

    @JvmDefault
    fun action(action: Action) {
        when (action) {
            GO_TO_SLEEP -> goToSleep()
            WAKE_UP     -> wakeUp()
            GO_HOME     -> goHome()
            GO_TO_MINE  -> goToMine()
            WORK        -> work()
            else        -> log.info { "Undefined action" }
        }
    }

    enum class Action {
        GO_TO_SLEEP,
        WAKE_UP,
        GO_HOME,
        GO_TO_MINE,
        WORK;
    }
}