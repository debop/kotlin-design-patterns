package io.kommons.designpatterns.delegation

import io.kommons.designpatterns.delegation.printers.CanonPrinter
import io.kommons.designpatterns.delegation.printers.EpsonPrinter
import io.kommons.designpatterns.delegation.printers.HpPrinter
import io.kommons.junit.jupiter.output.InMemoryAppender
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * DelegateTest
 */
class DelegateTest {

    companion object: KLogging() {
        const val MESSAGE = "Test Message Printed"
    }

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setup() {
        appender = InMemoryAppender()
    }

    @AfterEach
    fun cleanup() {
        appender.stop()
    }

    @Test
    fun `use Canon printer`() {
        val printerController = PrinterController(CanonPrinter())
        printerController.print(MESSAGE)

        appender.lastMessage shouldEqual "Canon Printer : $MESSAGE"
    }

    @Test
    fun `use Epson printer`() {
        val printerController = PrinterController(EpsonPrinter())
        printerController.print(MESSAGE)

        appender.lastMessage shouldEqual "Epson Printer : $MESSAGE"
    }

    @Test
    fun `use HP printer`() {
        val printerController = PrinterController(HpPrinter())
        printerController.print(MESSAGE)

        appender.lastMessage shouldEqual "HP Printer : $MESSAGE"
    }

}