package io.kommons.junit.jupiter.output

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldNotContain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

@CaptureSystemOutput
@TestMethodOrder(OrderAnnotation::class)
class CaptureSystemOutputExtensionTest {

    companion object: KLogging()

    @BeforeEach
    fun beforeEach(output: OutputCapture) {
        verifyOutput(output, "@BeforeEach")
    }

    @AfterEach
    fun afterEach(output: OutputCapture) {
        verifyOutput(output, "@AfterEach")
    }

    @Test
    @Order(1)
    fun `capture system output`(output: OutputCapture) {
        verifyOutput(output, "SYS OUT #1")
    }

    @Test
    @Order(2)
    fun `capture system error`(output: OutputCapture) {
        verifyError(output, "SYS ERR #2")
    }

    @Test
    @Order(3)
    fun `capture system out and err`(output: OutputCapture) {
        verifyOutput(output, "SYS OUT #2")
        verifyError(output, "SYS ERR #4")
    }

    private fun verifyOutput(output: OutputCapture, expected: String) {
        output.toString() shouldNotContain expected
        println(expected)
        output.expect { it shouldContain expected }
        output.expect { it shouldNotContain expected.toLowerCase() }
    }

    private fun verifyError(output: OutputCapture, expected: String) {
        output.toString() shouldNotContain expected
        println(expected)
        output.expect { it shouldContain expected }
        output.expect { it shouldNotContain expected.toLowerCase() }
    }
}