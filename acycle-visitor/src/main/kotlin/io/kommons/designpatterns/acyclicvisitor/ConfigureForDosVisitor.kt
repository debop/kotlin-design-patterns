package io.kommons.designpatterns.acyclicvisitor

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * ConfigureForDosVisitor class implements both zoom's and  hayes' visit method
 * for Dos manufacturer
 */
class ConfigureForDosVisitor: AllModemVisitor {

    companion object: KLogging()

    override fun visit(hayes: Hayes) {
        log.info { "$hayes used with Dos configurator." }
    }

    override fun visit(zoom: Zoom) {
        log.info { "$zoom used with Dos configurator." }
    }
}