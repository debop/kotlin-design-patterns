package io.kommons.designpatterns.bridge.enchantments

import io.kommons.designpatterns.bridge.Enchantment
import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * SoulEatingEnchantment
 *
 * @author debop
 */
class SoulEatingEnchantment: Enchantment {

    companion object: KLogging()

    override fun onActivate() {
        log.info { "The item spreads bloodlust." }
    }

    override fun invoke() {
        log.info { "The item eats the soul of enemies." }
    }

    override fun onDeactivate() {
        log.info { "Bloodlust slowly disappears." }
    }
}