package io.kommons.designpatterns.bridge

/**
 * 무기
 */
interface Weapon {

    fun wield()

    fun swing()

    fun unwield()

    val enchantment: Enchantment
}