package io.kommons.designpatterns.acyclicvisitor

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * Hayes class implements its accept method
 */
class Hayes: Modem() {

    companion object: KLogging()

    /**
     * Accepts all visitors but honors only HayesVisitor
     */
    override fun accept(modemVisitor: ModemVisitor) {
        when (modemVisitor) {
            is HayesVisitor -> modemVisitor.visit(this)
            else            -> log.info { "Only HayesVisitor is allowed to visit Hayes modem" }
        }
    }

    override fun toString(): String = "Hayes modem"
}