package io.kommons.designpatterns.visitor

/**
 * Interface for the nodes in hierachy
 */
interface Node {

    val children: MutableList<Node>

    @JvmDefault
    fun accept(visitor: NodeVisitor) {
        children.forEach {
            it.accept(visitor)
        }
    }
}
