package io.kommons.designpatterns.extensionobjects

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test

class UnitTest {

    companion object: KLogging()

    @Test
    fun `const get set`() {
        var name = "testName"
        var unit = Unit(name)

        unit.name shouldEqual name

        val newName = "newName"
        unit.name = newName

        unit.name shouldEqual newName

        unit.getUnitExtension("").shouldBeNull()
        unit.getUnitExtension("SoldierExtension").shouldBeNull()
        unit.getUnitExtension("SergeantExtension").shouldBeNull()
        unit.getUnitExtension("CommanderExtension").shouldBeNull()
    }

    @Test
    fun `SolderUnit test`() {
        val unit = SoldierUnit("SoldierUnitName")

        unit.getUnitExtension("SoldierExtension").shouldNotBeNull()
        unit.getUnitExtension("SergeantExtension").shouldBeNull()
        unit.getUnitExtension("CommanderExtension").shouldBeNull()
    }

    @Test
    fun `SergeantUnit test`() {
        val unit = SergeantUnit("SergeantUnitName")

        unit.getUnitExtension("SoldierExtension").shouldBeNull()
        unit.getUnitExtension("SergeantExtension").shouldNotBeNull()
        unit.getUnitExtension("CommanderExtension").shouldBeNull()
    }

    @Test
    fun `CommanderUnit test`() {
        val unit = CommanderUnit("CommanderUnitName")

        unit.getUnitExtension("SoldierExtension").shouldBeNull()
        unit.getUnitExtension("SergeantExtension").shouldBeNull()
        unit.getUnitExtension("CommanderExtension").shouldNotBeNull()
    }
}