package io.kommons.designpatterns.extensionobjects

interface UnitExtension

interface SoldierExtension: UnitExtension {
    fun soldierReady()
}

interface SergeantExtension: UnitExtension {
    fun sergeantReady()
}

interface CommanderExtension: UnitExtension {
    fun commanderReady()
}