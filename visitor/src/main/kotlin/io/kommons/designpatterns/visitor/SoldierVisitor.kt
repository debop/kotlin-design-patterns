package io.kommons.designpatterns.visitor

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * SoldierVisitor
 *
 * @author debop
 * @since 29/09/2019
 */
class SoldierVisitor: NodeVisitor {

    companion object: KLogging()

    override fun visit(node: Node) {
        when (node) {
            is Soldier -> log.info { "Greetings $node" }
            else       -> {
                // Nothing to do
            }
        }
    }
}