package io.kommons.designpatterns.facade

import io.kommons.junit.jupiter.output.InMemoryAppender
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldHaveSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DwarvenGoldmineFacadeTest {

    companion object: KLogging()

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun beforeEach() {
        appender = InMemoryAppender()
    }

    @AfterEach
    fun afterEach() {
        appender.stop()
    }

    @Test
    fun `full work day`() {

        val goldMine = DwarvenGoldmineFacade()
        goldMine.startNewDay()

        appender.messages shouldContain "Dwarf gold digger wakes up."
        appender.messages shouldContain "Dwarf cart operator wakes up."
        appender.messages shouldContain "Dwarven tunnel digger wakes up."

        appender.messages shouldContain "Dwarf gold digger goes to the mine."
        appender.messages shouldContain "Dwarf cart operator goes to the mine."
        appender.messages shouldContain "Dwarven tunnel digger goes to the mine."

        appender.messages shouldHaveSize 6

        goldMine.digOutGold()

        appender.messages shouldContain "Dwarf gold digger digs for gold."
        appender.messages shouldContain "Dwarf cart operator moves gold chunks out of the mine."
        appender.messages shouldContain "Dwarven tunnel digger creates another promising tunnel."

        appender.messages shouldHaveSize 9

        goldMine.endDay()

        appender.messages shouldContain "Dwarf gold digger goes home."
        appender.messages shouldContain "Dwarf cart operator goes home."
        appender.messages shouldContain "Dwarven tunnel digger goes home."

        appender.messages shouldContain "Dwarf gold digger goes to sleep."
        appender.messages shouldContain "Dwarf cart operator goes to sleep."
        appender.messages shouldContain "Dwarven tunnel digger goes to sleep."

        appender.messages shouldHaveSize 15
    }
}