package io.kommons.designpatterns.resource.acquisition.`is`.initialization

import io.kommons.logging.KotlinLogging
import io.kommons.logging.info

class App

private val log = KotlinLogging.logger { }

fun main() {

    SlidingDoor().use {
        log.info { "Walking in." }
    }

    TreasureChest().use {
        log.info { "Looting contents." }
    }
}