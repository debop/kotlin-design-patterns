package io.kommons.designpatterns.templatemethod

import io.kommons.logging.KLogging
import io.kommons.logging.debug

/**
 * HitAndRunMethod
 *
 * @author debop
 */
class HitAndRunMethod: StealingMethod {

    companion object: KLogging()

    override fun pickTarget(): String = "old goblin woman"

    override fun confuseTarget(target: String) {
        log.debug { "Approach the $target from behind." }
    }

    override fun stealTheItem(target: String) {
        log.debug { "Grab the handbag and run away fast!" }
    }
}