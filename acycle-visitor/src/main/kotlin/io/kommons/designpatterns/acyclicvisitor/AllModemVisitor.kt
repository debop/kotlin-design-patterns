package io.kommons.designpatterns.acyclicvisitor

/**
 * All ModemVisitor interface extends all visitor interfaces. This interface
 * provides ease of use when a visitor needs to visit all modem types.
 */
interface AllModemVisitor: ZoomVisitor, HayesVisitor {
}