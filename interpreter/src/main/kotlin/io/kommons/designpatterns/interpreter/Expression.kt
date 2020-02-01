package io.kommons.designpatterns.interpreter

abstract class Expression {

    abstract fun interpret(): Int

    abstract override fun toString(): String
}