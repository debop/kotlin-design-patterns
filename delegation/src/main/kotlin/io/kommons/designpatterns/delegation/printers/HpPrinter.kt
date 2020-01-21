package io.kommons.designpatterns.delegation.printers

import io.kommons.designpatterns.delegation.Printer
import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * HpPrinter
 */
class HpPrinter: Printer {

    companion object: KLogging()

    override fun print(message: String) {
        log.info { "HP Printer : $message" }
    }
}