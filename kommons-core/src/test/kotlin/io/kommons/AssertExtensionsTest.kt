package io.kommons

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AssertExtensionsTest {

    companion object: KLogging()

    class TestClass

    @Test
    fun `assert without -ea`() {
        AssertExtensionsTest::class.java.classLoader.setClassAssertionStatus(AssertExtensionsTest::class.qualifiedName, false)
        AssertExtensionsTest::class.java.desiredAssertionStatus().shouldBeFalse()
    }


    @Test
    fun `assert with -ea`() {
        TestClass::class.java.classLoader.setClassAssertionStatus(TestClass::class.qualifiedName, true)
        TestClass::class.java.desiredAssertionStatus().shouldBeTrue()
    }

    @Test
    fun `assert not null`() {
        var x: Long? = null
        assertThrows<AssertionError> {
            x.assertNotNull("x")
        }

        x = 12L
        x.assertNotNull("x")
    }

    @Test
    fun `assert not empty for string`() {
        var x: String? = null
        assertThrows<AssertionError> {
            x.assertNotEmpty("x")
        }

        x = ""
        assertThrows<AssertionError> {
            x.assertNotEmpty("x")
        }

        x = "    "
        x.assertNotEmpty("x")

        x = "  \t "
        x.assertNotEmpty("x")
    }

    @Test
    fun `assert not blank for string`() {
        var x: String? = null
        assertThrows<AssertionError> {
            x.assertNotBlank("x")
        }

        x = ""
        assertThrows<AssertionError> {
            x.assertNotBlank("x")
        }

        x = "    "
        assertThrows<AssertionError> {
            x.assertNotBlank("x")
        }

        x = "  \t "
        assertThrows<AssertionError> {
            x.assertNotBlank("x")
        }
    }
}