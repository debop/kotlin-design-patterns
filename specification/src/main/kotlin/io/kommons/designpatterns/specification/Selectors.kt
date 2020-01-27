package io.kommons.designpatterns.specification

abstract class AbstractSelector<T>: (T) -> Boolean {

    infix fun and(other: AbstractSelector<T>): AbstractSelector<T> {
        return ConjunctionSelector(this, other)
    }

    infix fun or(other: AbstractSelector<T>): AbstractSelector<T> {
        return DisjunctionSelector(this, other)
    }

    fun not(): AbstractSelector<T> {
        return NegationSelector(this)
    }
}

class ConjunctionSelector<T>(vararg selectors: AbstractSelector<T>): AbstractSelector<T>() {

    private val leafComponents = selectors.toList()

    override fun invoke(p1: T): Boolean {
        return leafComponents.all { it.invoke(p1) }
    }
}

class DisjunctionSelector<T>(vararg selectors: AbstractSelector<T>): AbstractSelector<T>() {

    private val leafComponents = selectors.toList()

    override fun invoke(p1: T): Boolean {
        return leafComponents.any { it.invoke(p1) }
    }
}

class NegationSelector<T>(private val selector: AbstractSelector<T>): AbstractSelector<T>() {

    override fun invoke(p1: T): Boolean {
        return !selector.invoke(p1)
    }
}

//class ColorSelector(private val color: Color): AbstractSelector<Creature>() {
//    override fun invoke(p1: Creature): Boolean = p1.color == color
//}

fun colorSelector(color: Color) = object: AbstractSelector<Creature>() {
    override fun invoke(p1: Creature): Boolean = p1.color == color
}

fun massEqualSelector(value: Double) = object: AbstractSelector<Creature>() {
    override fun invoke(p1: Creature): Boolean = p1.mass == Mass(value)
}

fun massGreaterThanSelector(value: Double) = object: AbstractSelector<Creature>() {
    override fun invoke(p1: Creature): Boolean = p1.mass > Mass(value)
}

fun massSmallerThanSelector(value: Double) = object: AbstractSelector<Creature>() {
    override fun invoke(p1: Creature): Boolean = p1.mass < Mass(value)
}

fun massSmallerThanOrEqSelector(value: Double) = object: AbstractSelector<Creature>() {
    override fun invoke(p1: Creature): Boolean = p1.mass <= Mass(value)
}


fun movementSelector(movement: Movement) = object: AbstractSelector<Creature>() {
    override fun invoke(p1: Creature): Boolean = p1.movement == movement
}

fun sizeSelector(size: Size) = object: AbstractSelector<Creature>() {
    override fun invoke(p1: Creature): Boolean = p1.size == size
}