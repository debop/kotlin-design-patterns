package io.kommons.designpatterns.bridge

import io.kommons.designpatterns.bridge.enchantments.FlyingEnchantment
import io.kommons.designpatterns.bridge.enchantments.SoulEatingEnchantment
import io.kommons.designpatterns.bridge.weapons.Hammer
import io.kommons.designpatterns.bridge.weapons.Sword

class App

fun main() {
    println("The knight receives an enchanted sword.")

    val enchantmentedSword = Sword(SoulEatingEnchantment())
    enchantmentedSword.wield()
    enchantmentedSword.swing()
    enchantmentedSword.unwield()

    println("The valkyrie receives an enchanted hammer.")

    val hammer = Hammer(FlyingEnchantment())
    hammer.wield()
    hammer.swing()
    hammer.unwield()

}