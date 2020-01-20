package io.kommons

import io.kommons.EnumExtensionsTest.DAYS.FRI
import io.kommons.EnumExtensionsTest.DAYS.MON
import io.kommons.EnumExtensionsTest.DAYS.SAT
import io.kommons.EnumExtensionsTest.DAYS.SUN
import io.kommons.EnumExtensionsTest.DAYS.THR
import io.kommons.EnumExtensionsTest.DAYS.TUE
import io.kommons.EnumExtensionsTest.DAYS.WED
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

/**
 * EnumExtensionsTest
 *
 * @author debop
 */
class EnumExtensionsTest {

    companion object: KLogging()

    enum class DAYS { MON, TUE, WED, THR, FRI, SAT, SUN }

    @Test
    fun `get enum map`() {
        DAYS::class.java.enumMap().keys shouldContainSame listOf("MON", "TUE", "WED", "THR", "FRI", "SAT", "SUN")
    }

    @Test
    fun `get enum list`() {
        DAYS::class.java.enumList() shouldContainSame listOf(MON, TUE, WED, THR, FRI, SAT, SUN)
    }

    @Test
    fun `get enum by name`() {
        DAYS::class.java.getByName("MON") shouldEqual MON

        DAYS::class.java.getByName("Mon").shouldBeNull()
    }

    @Test
    fun `check valid enum name`() {
        DAYS::class.java.isValidName("MON").shouldBeTrue()
        DAYS::class.java.isValidName("Mon").shouldBeFalse()
    }
}