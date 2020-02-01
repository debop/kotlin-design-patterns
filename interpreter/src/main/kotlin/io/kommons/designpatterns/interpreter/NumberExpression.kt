package io.kommons.designpatterns.interpreter

class NumberExpression(private val number: Int): Expression() {

    constructor(s: String): this(s.toInt())

    override fun interpret(): Int = number

    override fun toString(): String = "number"
}