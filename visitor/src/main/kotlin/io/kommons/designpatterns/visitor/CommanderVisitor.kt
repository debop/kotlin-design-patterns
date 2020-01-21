package io.kommons.designpatterns.visitor

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * CommanderVisitor
 *
 * @author debop
 * @since 29/09/2019
 */
class CommanderVisitor: NodeVisitor {

    companion object: KLogging()

    override fun visit(node: Node) {
        when (node) {
            is Commander -> log.info { "Good to see you $node" }
        }
    }
}