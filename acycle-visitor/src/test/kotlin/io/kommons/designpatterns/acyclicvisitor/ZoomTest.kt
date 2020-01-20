package io.kommons.designpatterns.acyclicvisitor

import io.kommons.logging.KLogging
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

/**
 * ZoomTest
 *
 * @author debop
 */
class ZoomTest {

    companion object: KLogging()

    @Test
    fun `accept for Dos`() {
        val zoom = Zoom()
        val mockVisitor = mockk<ConfigureForDosVisitor>(relaxUnitFun = true)

        zoom.accept(mockVisitor)
        verify(exactly = 1) { mockVisitor.visit(zoom) }
    }

    @Test
    fun `accept for Unix`() {
        val zoom = Zoom()
        val mockVisitor = mockk<ConfigureForUnixVisitor>(relaxUnitFun = true)

        zoom.accept(mockVisitor)
        verify(exactly = 1) { mockVisitor.visit(zoom) }
    }
}