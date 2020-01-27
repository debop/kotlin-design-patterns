package io.kommons.designpatterns.specification

import io.kommons.designpatterns.specification.Color.DARK
import io.kommons.designpatterns.specification.Color.RED
import io.kommons.designpatterns.specification.Movement.FLYING
import io.kommons.designpatterns.specification.Movement.SWIMMING
import io.kommons.designpatterns.specification.Movement.WALKING
import io.kommons.logging.KotlinLogging
import io.kommons.logging.info

class App

private val log = KotlinLogging.logger { }

fun main() {
    val creatures = listOf(Goblin(),
                           Octopus(),
                           Dragon(),
                           Shark(),
                           Troll(),
                           KillerBee())

    log.info { "Demonstrating hard coded specification:" }


    log.info { "Find all walking creatures" }
    print(creatures, movementSelector(WALKING))

    log.info { "Find all dark creatures" }
    print(creatures, colorSelector(DARK))

    log.info { "Demonstrating parameterized specification : " }
    log.info { "Find all creatures havier than 600kg" }
    print(creatures, massGreaterThanSelector(600.0))

    log.info { "Find all creatures lighter than or weighing exactly 500kg" }
    print(creatures, massSmallerThanOrEqSelector(500.0))

    log.info { "Demonstrating composite specification:" }
    log.info { "Find all red and flying creatures" }
    val redAndFlying = colorSelector(RED).and(movementSelector(FLYING))
    print(creatures, redAndFlying)

    log.info { "Find all scary creatures" }
    val scaryCreaturesSelector = colorSelector(DARK)
        .or(colorSelector(RED))
        .and(movementSelector(SWIMMING).not())
        .and(massGreaterThanSelector(400.0).or(massEqualSelector(400.0)))
    print(creatures, scaryCreaturesSelector)

}

private fun print(creatures: List<Creature>, selector: (Creature) -> Boolean) {
    creatures.filter { selector(it) }.forEach { log.info { it } }
    log.info { "" }
}