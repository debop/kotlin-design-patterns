package io.kommons.designpatterns.acyclicvisitor

/**
 *  HayesVisitor interface
 */
interface HayesVisitor: ModemVisitor {

    fun visit(hayes: Hayes)

}