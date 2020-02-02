package io.kommons.designpatterns.valueobject

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
import org.junit.jupiter.api.Test

class HeroStatTest {

    companion object: KLogging()

    private val heroStatA = HeroStat(3, 9, 2)
    private val heroStatB = HeroStat(3, 9, 2)
    private val heroStatC = HeroStat(1, 2, 3)

    @Test
    fun `equals value object`() {
        heroStatA shouldEqual heroStatB
        heroStatC shouldNotEqual heroStatA
    }

    @Test
    fun `toString value object`() {
        heroStatA.toString() shouldEqual heroStatB.toString()
        heroStatC.toString() shouldNotEqual heroStatA.toString()
    }
}