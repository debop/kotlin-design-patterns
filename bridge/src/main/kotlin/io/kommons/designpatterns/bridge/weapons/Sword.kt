package io.kommons.designpatterns.bridge.weapons

import io.kommons.designpatterns.bridge.Enchantment
import io.kommons.designpatterns.bridge.Weapon
import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * Sword
 *
 * @author debop
 */
class Sword(override val enchantment: Enchantment): Weapon {

    companion object: KLogging()

    override fun wield() {
        log.info { "The sword is wielded." }
        enchantment.onActivate()
    }

    override fun swing() {
        log.info { "The sword is swinged." }
        enchantment.invoke()
    }

    override fun unwield() {
        log.info { "The sword is unwielded." }
        enchantment.onDeactivate()
    }

}