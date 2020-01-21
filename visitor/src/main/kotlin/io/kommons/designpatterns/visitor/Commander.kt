package io.kommons.designpatterns.visitor

/**
 * Commander
 *
 * @author debop
 * @since 29/09/2019
 */
class Commander(override val children: MutableList<Node> = mutableListOf()): Node {

    override fun accept(visitor: NodeVisitor) {
        visitor.visit(this)
        super.accept(visitor)
    }

    override fun toString(): String = "commander"
}