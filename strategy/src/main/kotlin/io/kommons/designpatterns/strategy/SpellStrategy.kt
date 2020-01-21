package io.kommons.designpatterns.strategy

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * SpellStrategy
 *
 * @author debop
 */
class SpellStrategy: DragonSlayingStrategy {

    companion object: KLogging()

    override fun execute() {
        log.info {
            "당신은 파괴의 주문을 하고, 용은 먼지 더미 속으로 증발해버린다!"
        }
    }
}