package io.kommons.designpatterns.bridge

import io.kommons.designpatterns.bridge.enchantments.FlyingEnchantment
import io.kommons.designpatterns.bridge.weapons.Hammer
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Test

class HammerTest: AbstractWeaponTest() {

    @Test
    fun `test hammer`() {
        val hammer = spyk(Hammer(mockk<FlyingEnchantment>(relaxUnitFun = true)))
        testBasicWeaponActions(hammer)
    }
}