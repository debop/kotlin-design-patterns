package io.kommons.designpatterns.command

/**
 * Goblin is the target of the spells
 */
class Goblin: Target() {

    init {
        size = Size.NORMAL
        visibility = Visibility.VISIBLE
    }

    override fun toString(): String = "Goblin"
}