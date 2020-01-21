package io.kommons.designpatterns.decorator

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * Kotlin에서 지원하는 class delegation 기능을 활용하면 좀 더 compact하게 구현 할 수 있습니다.
 */
class ClubbedTroll(private val decorated: Troll): Troll by decorated {

    companion object: KLogging()

    override fun attach() {
        decorated.attach()
        log.info { "The troll swings at you with a club!" }
    }

    override fun getAttackPower(): Int {
        return decorated.getAttackPower() + 10
    }

}