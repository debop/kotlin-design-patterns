package io.kommons.designpatterns.strategy

import io.kommons.logging.KLogging
import io.kommons.logging.debug

/**
 * ProjectieStrategy
 *
 * @author debop
 */
class ProjectileStrategy: DragonSlayingStrategy {

    companion object: KLogging()

    override fun execute() {
        log.debug {
            "당신이 마법의 석궁으로 용을 쏘면 용은 땅에 쓰러져 죽는다."
        }
    }
}