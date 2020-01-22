package io.kommons.designpatterns.extensionobjects

import io.kommons.logging.KotlinLogging
import io.kommons.logging.info

class App

fun main() {

    // create 3 three units
    val soldierUnit = SoldierUnit("SoldierUnit1")
    val sergeantUnit = SergeantUnit("SergeantUnit1")
    val commanderUnit = CommanderUnit("CommanderUnit1")

    // check for each unit to have an extension
    checkExtensionsForUnit(soldierUnit)
    checkExtensionsForUnit(sergeantUnit)
    checkExtensionsForUnit(commanderUnit)
}

internal fun checkExtensionsForUnit(unit: Unit) {
    val log = KotlinLogging.logger { }

    log.info { "Start check extensions unit=${unit.name}" }

    var name = unit.name
    val func: (String) -> () -> kotlin.Unit = { str: String -> { log.info { "$name without $str" } } }

    var extension = "SoldierExtension"
    unit.getUnitExtension(extension)?.let {
        (it as? SoldierExtension)?.soldierReady()
    } ?: func.invoke(extension).invoke()

    extension = "SergeantExtension"
    unit.getUnitExtension(extension)?.let {
        (it as? SergeantExtension)?.sergeantReady()
    } ?: func.invoke(extension).invoke()

    extension = "CommanderExtension"
    unit.getUnitExtension(extension)?.let {
        (it as CommanderExtension).commanderReady()
    } ?: func.invoke(extension).invoke()
}