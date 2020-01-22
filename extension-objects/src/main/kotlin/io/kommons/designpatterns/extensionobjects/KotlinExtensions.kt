package io.kommons.designpatterns.extensionobjects

import io.kommons.logging.KotlinLogging
import io.kommons.logging.info

private val log = KotlinLogging.logger { }

fun Soldier.ready() {
    log.info { "[Soldier] ${unit.name} is ready in Kotlin extension method." }
}

fun Sergeant.ready() {
    log.info { "[Sergeant] ${unit.name} is ready in Kotlin extension method." }
}

fun Commander.ready() {
    log.info { "[Commander] ${unit.name} is ready in Kotlin extension method." }
}