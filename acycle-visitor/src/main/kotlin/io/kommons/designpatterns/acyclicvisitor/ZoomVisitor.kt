package io.kommons.designpatterns.acyclicvisitor

/**
 * ZoomVisitor interface
 *
 * @author debop
 */
interface ZoomVisitor: ModemVisitor {

    fun visit(zoom: Zoom)

}