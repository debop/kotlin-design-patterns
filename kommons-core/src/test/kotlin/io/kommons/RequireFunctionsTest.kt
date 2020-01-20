package io.kommons

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * RequireFunctionsTest
 *
 * @author debop
 */
class RequireFunctionsTest {

    companion object: KLogging()

    @Test
    fun `assert without -ea`() {
        RequireFunctionsTest::class.java.classLoader.setClassAssertionStatus(RequireFunctionsTest::class.qualifiedName, false)
        RequireFunctionsTest::class.java.desiredAssertionStatus().shouldBeFalse()
    }

    class TestClass

    @Test
    fun `assert with -ea`() {
        TestClass::class.java.classLoader.setClassAssertionStatus(TestClass::class.qualifiedName, true)
        TestClass::class.java.desiredAssertionStatus().shouldBeTrue()
    }

    @Test
    fun `require not null`() {
        var x: Long? = null
        assertThrows<IllegalArgumentException> {
            x.requireNotNull("x")
        }

        x = 12L
        x.requireNotNull("x")
    }

    @Test
    fun `require not empty for string`() {
        var x: String? = null
        assertThrows<IllegalArgumentException> {
            x.requireNotEmpty("x")
        }

        x = ""
        assertThrows<IllegalArgumentException> {
            x.requireNotEmpty("x")
        }

        x = "    "
        x.requireNotEmpty("x")

        x = "  \t "
        x.requireNotEmpty("x")
    }

    @Test
    fun `require not blank for string`() {
        var x: String? = null
        assertThrows<IllegalArgumentException> {
            x.requireNotBlank("x")
        }

        x = ""
        assertThrows<IllegalArgumentException> {
            x.requireNotBlank("x")
        }

        x = "    "
        assertThrows<IllegalArgumentException> {
            x.requireNotBlank("x")
        }

        x = "  \t "
        assertThrows<IllegalArgumentException> {
            x.requireNotBlank("x")
        }
    }
}