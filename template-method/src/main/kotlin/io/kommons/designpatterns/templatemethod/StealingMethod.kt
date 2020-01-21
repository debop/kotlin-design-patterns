package io.kommons.designpatterns.templatemethod

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * StealingMethod defines skeleton for the algorithm.
 */
interface StealingMethod {

    companion object: KLogging()

    fun pickTarget(): String

    fun confuseTarget(target: String)

    fun stealTheItem(target: String)

    @JvmDefault
    fun steal() {
        val target = pickTarget()
        log.info { "The target has been chosen as $target" }
        confuseTarget(target)
        stealTheItem(target)
    }
}