package io.kommons.designpatterns.extensionobjects

import io.kommons.junit.jupiter.output.InMemoryAppender
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ConcreteExtensionTest {

    companion object: KLogging()

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun beforeEach() {
        appender = InMemoryAppender()
    }

    @AfterEach
    fun afterEach() {
        appender.clear()
    }

    @Test
    fun `soldier test`() {
        val soldier = Soldier(SoldierUnit("SoldierUnitTest"))
        soldier.soldierReady()

        appender.lastMessage shouldEqual "[Soldier] SoldierUnitTest is ready"
    }

    @Test
    fun `sergeant test`() {
        val sergeant = Sergeant(SergeantUnit("SergeantUnitTest"))
        sergeant.sergeantReady()

        appender.lastMessage shouldEqual "[Sergeant] SergeantUnitTest is ready"
    }

    @Test
    fun `commander test`() {
        val commander = Commander(CommanderUnit("CommanderUnitTest"))
        commander.commanderReady()

        appender.lastMessage shouldEqual "[Commander] CommanderUnitTest is ready"
    }
}