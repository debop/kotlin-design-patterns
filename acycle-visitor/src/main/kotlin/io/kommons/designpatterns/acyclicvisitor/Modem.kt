package io.kommons.designpatterns.acyclicvisitor

/**
 * Modem abstract class
 */
abstract class Modem {

    abstract fun accept(modemVisitor: ModemVisitor)

}