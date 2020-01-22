package io.kommons.designpatterns.factorykit

import io.kommons.logging.KotlinLogging
import io.kommons.logging.info

class App

private val log = KotlinLogging.logger { }

fun main() {
    val factory = WeaponFactory.factory { builder ->
        builder.add(WeaponType.SWORD, ::Sword)
        builder.add(WeaponType.AXE, ::Axe)
        builder.add(WeaponType.SPEAR, ::Spear)
        builder.add(WeaponType.BOW, ::Bow)
    }

    val axe = factory.create(WeaponType.AXE)
    log.info { "$axe" }
}