package io.kommons.designpatterns.visitor

/**
 * Soldier
 *
 * @author debop
 * @since 29/09/2019
 */
class Soldier(override val children: MutableList<Node> = mutableListOf()): Node {

    override fun accept(visitor: NodeVisitor) {
        visitor.visit(this)
        super.accept(visitor)
    }

    override fun toString(): String = "soldier"
}