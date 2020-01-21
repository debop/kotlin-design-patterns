package io.kommons.designpatterns.decorator

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * SimpleTroll
 */
class SimpleTroll: Troll {

    companion object: KLogging()

    override fun attach() {
        log.info { "The troll tries to grab you!" }
    }

    override fun getAttackPower(): Int {
        return 10
    }

    override fun fleeBattle() {
        log.info { "The troll shrieks in horror and runs away!" }
    }
}