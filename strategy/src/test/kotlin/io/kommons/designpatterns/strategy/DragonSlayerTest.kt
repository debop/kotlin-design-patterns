package io.kommons.designpatterns.strategy

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

/**
 * DragonSlayerTest
 *
 * @author debop
 */
class DragonSlayerTest {

    @Test
    fun `go to battle`() {
        val strategy = mockk<DragonSlayingStrategy>(relaxUnitFun = true)
        val dragonSlayer = DragonSlayer(strategy)

        dragonSlayer.goToBattle()

        verify(exactly = 1) { strategy.execute() }
    }

    @Test
    fun `change strategy`() {
        val initialStrategy = mockk<DragonSlayingStrategy>(relaxUnitFun = true)
        val dragonSlayer = DragonSlayer(initialStrategy)

        dragonSlayer.goToBattle()
        verify(exactly = 1) { initialStrategy.execute() }

        val newStrategy = mockk<DragonSlayingStrategy>(relaxUnitFun = true)
        dragonSlayer.changeStrategy(newStrategy)

        dragonSlayer.goToBattle()
        verify(exactly = 1) { newStrategy.execute() }
    }
}