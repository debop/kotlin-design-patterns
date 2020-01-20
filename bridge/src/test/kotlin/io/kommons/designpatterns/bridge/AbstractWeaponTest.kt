package io.kommons.designpatterns.bridge

import io.mockk.confirmVerified
import io.mockk.verify
import org.amshove.kluent.shouldNotBeNull

/**
 * AbstractWeaponTest
 *
 * @author debop
 */
abstract class AbstractWeaponTest {

    fun testBasicWeaponActions(weapon: Weapon) {
        val enchantment = weapon.enchantment
        enchantment.shouldNotBeNull()

        weapon.swing()
        verify { enchantment.invoke() }
        confirmVerified(enchantment)

        weapon.wield()
        verify { enchantment.onActivate() }
        confirmVerified(enchantment)

        weapon.unwield()
        verify { enchantment.onDeactivate() }
        confirmVerified(enchantment)
    }

}