package io.kommons.designpatterns.visitor

/**
 * Visitor interface
 *
 * @author debop
 * @since 29/09/2019
 */
interface NodeVisitor {

    /**
     * Visit to node
     *
     * @param node
     */
    fun visit(node: Node)

}