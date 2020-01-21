package io.kommons.designpatterns.templatemethod

import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.Test

/**
 * HalflingThiefTest
 *
 * @author debop
 */
class HalflingThiefTest {

    @Test
    fun `run steal`() {
        val method = mockk<StealingMethod>(relaxUnitFun = true)
        val thief = HalflingThief(method)

        thief.steal()
        verify(exactly = 1) { method.steal() }
    }

    @Test
    fun `run changeMethod`() {
        val initialMethod = mockk<StealingMethod>(relaxUnitFun = true)
        val thief = HalflingThief(initialMethod)

        thief.steal()
        verify(exactly = 1) { initialMethod.steal() }

        val newMethod = mockk<StealingMethod>(relaxUnitFun = true)
        thief.changeMethod(newMethod)

        thief.steal()
        verify(exactly = 1) { newMethod.steal() }

        verifyOrder {
            initialMethod.steal()
            newMethod.steal()
        }
    }
}