package io.kommons.designpatterns.strategy

import io.kommons.logging.KLogging
import io.kommons.logging.debug

/**
 * MelleStrategy
 *
 * @author debop
 */
class MeleeStrategy: DragonSlayingStrategy {

    companion object: KLogging()

    override fun execute() {
        log.debug {
            "너의 엑스칼리버로 용의 머리를 잘라라!"
        }
    }
}