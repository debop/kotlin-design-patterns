package io.kommons.designpatterns.specification

import io.kommons.designpatterns.specification.Color.GREEN
import io.kommons.designpatterns.specification.Color.RED
import io.kommons.designpatterns.specification.Movement.FLYING
import io.kommons.designpatterns.specification.Movement.SWIMMING
import io.kommons.designpatterns.specification.Size.NORMAL
import io.kommons.designpatterns.specification.Size.SMALL
import io.kommons.logging.KLogging
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Test

class SelectorTest {

    companion object: KLogging()

    @Test
    fun `select by color`() {
        val greenCreature = mockk<Creature>(relaxUnitFun = true)
        every { greenCreature.color } returns GREEN

        val redCreature = mockk<Creature>(relaxUnitFun = true)
        every { redCreature.color } returns RED

        val greenSelector = colorSelector(GREEN)

        greenSelector.invoke(greenCreature).shouldBeTrue()
        greenSelector.invoke(redCreature).shouldBeFalse()
    }

    @Test
    fun `select by mass`() {
        val lightCreature = mockk<Creature>()
        every { lightCreature.mass } returns Mass(50.0)

        val heavyCreature = mockk<Creature>()
        every { heavyCreature.mass } returns Mass(2500.0)

        val lightSelector = massSmallerThanOrEqSelector(500.0)

        lightSelector(lightCreature).shouldBeTrue()
        lightSelector(heavyCreature).shouldBeFalse()
    }

    @Test
    fun `select by movement`() {
        val swimmingCreature = mockk<Creature>()
        every { swimmingCreature.movement } returns SWIMMING

        val flyingCreature = mockk<Creature>()
        every { flyingCreature.movement } returns FLYING

        val swimmingSelector = movementSelector(SWIMMING)

        swimmingSelector(swimmingCreature).shouldBeTrue()
        swimmingSelector(flyingCreature).shouldBeFalse()
    }

    @Test
    fun `select by size`() {
        val normalCreature = mockk<Creature>()
        every { normalCreature.size } returns NORMAL

        val smallCreature = mockk<Creature>()
        every { smallCreature.size } returns SMALL

        val normalSelector = sizeSelector(NORMAL)

        normalSelector(normalCreature).shouldBeTrue()
        normalSelector(smallCreature).shouldBeFalse()
    }

    @Test
    fun `and composite`() {
        val swimmingHeavyCreature = mockk<Creature>()
        every { swimmingHeavyCreature.movement } returns SWIMMING
        every { swimmingHeavyCreature.mass } returns Mass(100.0)

        val swimmingLightCreature = mockk<Creature>()
        every { swimmingLightCreature.movement } returns SWIMMING
        every { swimmingLightCreature.mass } returns Mass(25.0)

        val lightAndSwimmingSelector =
            massSmallerThanOrEqSelector(50.0) and movementSelector(SWIMMING)

        lightAndSwimmingSelector(swimmingHeavyCreature).shouldBeFalse()
        lightAndSwimmingSelector(swimmingLightCreature).shouldBeTrue()
    }

    @Test
    fun `or composite`() {
        val swimmingHeavyCreature = mockk<Creature>()
        every { swimmingHeavyCreature.movement } returns SWIMMING
        every { swimmingHeavyCreature.mass } returns Mass(100.0)

        val swimmingLightCreature = mockk<Creature>()
        every { swimmingLightCreature.movement } returns SWIMMING
        every { swimmingLightCreature.mass } returns Mass(25.0)

        val lightAndSwimmingSelector =
            massSmallerThanOrEqSelector(50.0) or movementSelector(SWIMMING)

        lightAndSwimmingSelector(swimmingHeavyCreature).shouldBeTrue()
        lightAndSwimmingSelector(swimmingLightCreature).shouldBeTrue()
    }

    @Test
    fun `not composite`() {
        val swimmingHeavyCreature = mockk<Creature>()
        every { swimmingHeavyCreature.movement } returns SWIMMING
        every { swimmingHeavyCreature.mass } returns Mass(100.0)

        val swimmingLightCreature = mockk<Creature>()
        every { swimmingLightCreature.movement } returns SWIMMING
        every { swimmingLightCreature.mass } returns Mass(25.0)

        val heavySelector = massSmallerThanOrEqSelector(50.0).not()

        heavySelector(swimmingHeavyCreature).shouldBeTrue()
        heavySelector(swimmingLightCreature).shouldBeFalse()
    }
}