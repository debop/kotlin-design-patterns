package io.kommons.designpatterns.visitor

/**
 * Sergeant
 *
 * @author debop
 * @since 29/09/2019
 */
class Sergeant(override val children: MutableList<Node> = mutableListOf()): Node {

    override fun accept(visitor: NodeVisitor) {
        visitor.visit(this)
        super.accept(visitor)
    }

    override fun toString(): String = "sergeant"
}