package io.kommons.designpatterns.visitor

import io.kommons.junit.jupiter.output.InMemoryAppender
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * AbstractVisitorTest
 *
 * @author debop
 * @since 29/09/2019
 */
abstract class AbstractVisitorTest<V: NodeVisitor>(private val visitor: V,
                                                   private val commanderResponse: String? = null,
                                                   private val sergeantResponse: String? = null,
                                                   private val soldierResponse: String? = null) {

    companion object: KLogging()

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setup() {
        appender = InMemoryAppender()
    }

    @AfterEach
    fun cleanup() {
        appender.stop()
    }

    @Test
    fun `visit commander`() {
        commanderResponse?.let {
            this.visitor.visit(Commander())

            appender.lastMessage shouldEqual it
            appender.size shouldEqualTo 1
        }
    }

    @Test
    fun `visit sergeant`() {
        sergeantResponse?.let {
            this.visitor.visit(Sergeant())

            appender.lastMessage shouldEqual it
            appender.size shouldEqualTo 1
        }
    }

    @Test
    fun `visit soldier`() {
        soldierResponse?.let {
            this.visitor.visit(Soldier())

            appender.lastMessage shouldEqual it
            appender.size shouldEqualTo 1
        }
    }
}