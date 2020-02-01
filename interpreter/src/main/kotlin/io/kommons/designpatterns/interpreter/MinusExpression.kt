package io.kommons.designpatterns.interpreter

class MinusExpression(private val leftExpr: Expression,
                      private val rightExpr: Expression): Expression() {

    override fun interpret(): Int =
        leftExpr.interpret() - rightExpr.interpret()

    override fun toString(): String = "-"

}