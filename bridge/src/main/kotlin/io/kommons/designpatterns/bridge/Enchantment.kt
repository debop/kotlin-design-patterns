package io.kommons.designpatterns.bridge

/**
 * 마법
 */
interface Enchantment {

    fun onActivate()

    fun invoke()

    fun onDeactivate()
}