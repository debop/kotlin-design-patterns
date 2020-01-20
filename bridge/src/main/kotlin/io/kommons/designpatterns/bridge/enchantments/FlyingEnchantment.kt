package io.kommons.designpatterns.bridge.enchantments

import io.kommons.designpatterns.bridge.Enchantment
import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * FlyingEnchantment
 *
 * @author debop
 */
class FlyingEnchantment: Enchantment {

    companion object: KLogging()

    override fun onActivate() {
        log.info { "The item begins to glow faintly." }
    }

    override fun invoke() {
        log.info { "The item flies and strikes the enemies finally returning to owner's hand." }
    }

    override fun onDeactivate() {
        log.info { "The item's glow fades." }
    }
}