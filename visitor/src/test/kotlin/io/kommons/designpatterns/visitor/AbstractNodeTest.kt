package io.kommons.designpatterns.visitor

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

/**
 * UnitTest
 *
 * @author debop
 * @since 29/09/2019
 */
abstract class AbstractNodeTest<N: Node>(val factory: (MutableList<Node>) -> N) {

    @Test
    fun `node accept`() {
        val nodes = MutableList(5) { mockk<Node>(relaxUnitFun = true) }

        val node = factory.invoke(nodes)
        val visitor = mockk<NodeVisitor>(relaxUnitFun = true)

        node.accept(visitor)
        verifyVisit(node, visitor)

        nodes.forEach { n ->
            verify { n.accept(visitor) }
        }
    }

    fun verifyVisit(node: N, visitor: NodeVisitor) {
        verify { visitor.visit(node) }
    }
}