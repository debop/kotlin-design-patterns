package io.kommons.designpatterns.specification

import io.kommons.designpatterns.specification.Color.DARK
import io.kommons.designpatterns.specification.Color.GREEN
import io.kommons.designpatterns.specification.Color.LIGHT
import io.kommons.designpatterns.specification.Color.RED
import io.kommons.designpatterns.specification.Movement.FLYING
import io.kommons.designpatterns.specification.Movement.SWIMMING
import io.kommons.designpatterns.specification.Movement.WALKING
import io.kommons.designpatterns.specification.Size.LARGE
import io.kommons.designpatterns.specification.Size.NORMAL
import io.kommons.designpatterns.specification.Size.SMALL

interface Creature {
    val name: String
    val size: Size
    val movement: Movement
    val color: Color
    val mass: Mass
}

abstract class AbstractCreature(override val name: String,
                                override val size: Size,
                                override val movement: Movement,
                                override val color: Color,
                                override val mass: Mass): Creature {
    override fun toString(): String =
        "$name [size=$size, movement=$movement, color=$color, mass=$mass]"
}

class Dragon @JvmOverloads constructor(mass: Mass = Mass(39300.0))
    : AbstractCreature("Dragon", LARGE, FLYING, RED, mass)

class Goblin @JvmOverloads constructor(mass: Mass = Mass(30.0))
    : AbstractCreature("Goblin", SMALL, WALKING, GREEN, mass)

class KillerBee @JvmOverloads constructor(mass: Mass = Mass(6.7))
    : AbstractCreature("KillerBee", SMALL, FLYING, LIGHT, mass)

class Octopus @JvmOverloads constructor(mass: Mass = Mass(12.0))
    : AbstractCreature("Octopus", NORMAL, SWIMMING, DARK, mass)

class Shark @JvmOverloads constructor(mass: Mass = Mass(500.0))
    : AbstractCreature("Shark", NORMAL, SWIMMING, LIGHT, mass)

class Troll @JvmOverloads constructor(mass: Mass = Mass(4000.0))
    : AbstractCreature("Troll", LARGE, WALKING, DARK, mass)
