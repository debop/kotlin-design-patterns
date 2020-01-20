package io.kommons.designpatterns.bridge

import io.kommons.designpatterns.bridge.enchantments.FlyingEnchantment
import io.kommons.designpatterns.bridge.weapons.Sword
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Test

/**
 * SwordTest
 *
 * @author debop
 */
class SwordTest: AbstractWeaponTest() {

    @Test
    fun `test sword`() {
        val sword = spyk(Sword(mockk<FlyingEnchantment>(relaxUnitFun = true)))
        testBasicWeaponActions(sword)
    }
}