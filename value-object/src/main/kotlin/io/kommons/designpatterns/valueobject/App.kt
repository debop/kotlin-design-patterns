package io.kommons.designpatterns.valueobject

import io.kommons.logging.KotlinLogging
import io.kommons.logging.info

class App

private val log = KotlinLogging.logger { }

fun main() {
    val statA = HeroStat(10, 5, 0)
    val statB = HeroStat(10, 5, 0)
    val statC = HeroStat(5, 1, 8)

    log.info { "$statA" }

    log.info { "Is statA and statB equal: ${statA == statB}" }
    log.info { "Is statA and statC equal: ${statA == statC}" }
}