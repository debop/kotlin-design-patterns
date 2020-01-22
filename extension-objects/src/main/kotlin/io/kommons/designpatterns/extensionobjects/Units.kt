package io.kommons.designpatterns.extensionobjects

open class Unit(var name: String) {
    protected val unitExtension: UnitExtension? = null

    open fun getUnitExtension(extensionName: String): UnitExtension? = null
}

class SoldierUnit(name: String): Unit(name) {

    override fun getUnitExtension(extensionName: String): UnitExtension? {
        return when (extensionName) {
            "SoldierExtension" -> unitExtension ?: Soldier(this)
            else               -> super.getUnitExtension(extensionName)
        }
    }
}

class SergeantUnit(name: String): Unit(name) {

    override fun getUnitExtension(extensionName: String): UnitExtension? {
        return when (extensionName) {
            "SergeantExtension" -> unitExtension ?: Sergeant(this)
            else                -> super.getUnitExtension(extensionName)
        }
    }
}

class CommanderUnit(name: String): Unit(name) {

    override fun getUnitExtension(extensionName: String): UnitExtension? {
        return when (extensionName) {
            "CommanderExtension" -> unitExtension ?: Commander(this)
            else                 -> super.getUnitExtension(extensionName)
        }
    }
}