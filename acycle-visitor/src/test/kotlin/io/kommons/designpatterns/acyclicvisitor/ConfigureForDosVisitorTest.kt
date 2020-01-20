package io.kommons.designpatterns.acyclicvisitor

import io.kommons.junit.jupiter.output.InMemoryAppender
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode

/**
 * ConfigureForDosVisitorTest
 *
 * @author debop
 */
@Execution(ExecutionMode.SAME_THREAD)
class ConfigureForDosVisitorTest {

    companion object: KLogging()

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setup() {
        appender = InMemoryAppender(ConfigureForDosVisitor::class)
    }

    @AfterEach
    fun cleanup() {
        appender.stop()
    }

    @Test
    fun `visit for zoom`() {
        val visitor = ConfigureForDosVisitor()
        val zoom = Zoom()

        visitor.visit(zoom)

        appender.lastMessage shouldEqual "$zoom used with Dos configurator."
    }

    @Test
    fun `visit for hayes`() {
        val visitor = ConfigureForDosVisitor()
        val hayes = Hayes()

        visitor.visit(hayes)

        appender.lastMessage shouldEqual "$hayes used with Dos configurator."
    }
}