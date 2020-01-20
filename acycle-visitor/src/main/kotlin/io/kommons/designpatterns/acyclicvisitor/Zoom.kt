package io.kommons.designpatterns.acyclicvisitor

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * Zoom
 *
 * @author debop
 */
class Zoom: Modem() {

    companion object: KLogging()

    override fun accept(modemVisitor: ModemVisitor) {
        when (modemVisitor) {
            is ZoomVisitor -> modemVisitor.visit(this)
            else           -> log.info { "Only ZoomVisitor is allowed to visit Zoom modem" }
        }
    }

    override fun toString(): String = "Zoom modem"
}