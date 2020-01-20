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
 * ConfigureForUnixVisitorTest
 *
 * @author debop
 */
@Execution(ExecutionMode.SAME_THREAD)
class ConfigureForUnixVisitorTest {

    companion object: KLogging()

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setup() {
        appender = InMemoryAppender(ConfigureForUnixVisitor::class)
    }

    @AfterEach
    fun cleanup() {
        appender.stop()
    }

    @Test
    fun `visit for zoom`() {
        val visitor = ConfigureForUnixVisitor()
        val zoom = Zoom()

        visitor.visit(zoom)

        appender.lastMessage shouldEqual "$zoom used with Unix configurator."
    }
}