package io.kommons.designpatterns.acyclicvisitor

import io.kommons.logging.KLogging
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

/**
 * HayesTest
 *
 * @author debop
 */
class HayesTest {

    companion object: KLogging()

    @Test
    fun `accept for Dos`() {
        val hayes = Hayes()
        val mockVisitor = mockk<ConfigureForDosVisitor>(relaxUnitFun = true)

        hayes.accept(mockVisitor)
        verify(exactly = 1) { mockVisitor.visit(hayes) }
    }

    @Test
    fun `accept for Unix`() {
        val hayes = Hayes()
        val mockVisitor = mockk<ConfigureForUnixVisitor>(relaxUnitFun = true)

        hayes.accept(mockVisitor)
        verify(exactly = 0) { mockVisitor.visit(any()) }
    }
}