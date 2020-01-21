package io.kommons.designpatterns.templatemethod

import io.kommons.junit.jupiter.output.InMemoryAppender
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode

@Execution(ExecutionMode.SAME_THREAD)
abstract class StealingMethodTest<M: StealingMethod>(private val method: M,
                                                     private val expectedTarget: String,
                                                     private val expectedTargetResult: String,
                                                     private val expectedConfuseMethod: String,
                                                     private val expectedStealMethod: String) {

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setup() {
        appender = InMemoryAppender()
    }

    @AfterEach
    fun cleanup() {
        appender.stop()
    }

    /**
     * Verify if the target confusing step goes as planned
     */
    @Test
    fun `verify confuseTarget`() {
        appender.size shouldEqualTo 0

        this.method.confuseTarget(expectedTarget)
        appender.lastMessage shouldEqual this.expectedConfuseMethod
        appender.size shouldEqualTo 1
    }

    /**
     * Verify if the stealing step goes as planned
     */
    @Test
    fun `verify stealTheItem`() {
        appender.size shouldEqualTo 0

        this.method.stealTheItem(this.expectedTarget)
        appender.lastMessage shouldEqual this.expectedStealMethod
        appender.size shouldEqualTo 1
    }

    /**
     * Verify if the complete steal process goes as planned
     */
    @Test
    fun `verify steal`() {
        this.method.steal()

        val logs = appender.messages.toList()
        logs.contains(this.expectedTargetResult)
        logs.contains(this.expectedConfuseMethod)
        logs.contains(this.expectedStealMethod)
        appender.size shouldEqualTo 3
    }
}