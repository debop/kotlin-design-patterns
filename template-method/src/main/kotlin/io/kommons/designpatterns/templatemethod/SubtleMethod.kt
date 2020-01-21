package io.kommons.designpatterns.templatemethod

import io.kommons.logging.KLogging
import io.kommons.logging.debug

/**
 * SubtleMethod implementation of [StealingMethod].
 */
class SubtleMethod: StealingMethod {

    companion object: KLogging()

    override fun pickTarget(): String = "shop keeper"

    override fun confuseTarget(target: String) {
        log.debug { "Approach the $target with tears running and hug him!" }
    }

    override fun stealTheItem(target: String) {
        log.debug { "While in close contact grab the $target's wallet." }
    }
}