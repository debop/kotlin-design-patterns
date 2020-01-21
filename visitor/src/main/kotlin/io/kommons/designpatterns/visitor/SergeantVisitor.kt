package io.kommons.designpatterns.visitor

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * SergeantVisitor
 *
 * @author debop
 * @since 29/09/2019
 */
class SergeantVisitor: NodeVisitor {

    companion object: KLogging()

    override fun visit(node: Node) {
        when (node) {
            is Sergeant -> log.info { "Hello $node" }
        }
    }
}