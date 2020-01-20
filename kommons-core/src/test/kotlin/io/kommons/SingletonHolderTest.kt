package io.kommons

import io.kommons.junit.jupiter.output.InMemoryAppender
import io.kommons.logging.KotlinLogging
import io.kommons.logging.debug
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test
import kotlin.streams.toList

class SingletonHolderTest {

    class Manager private constructor(private val name: String) {
        companion object: SingletonHolder<Manager>({ Manager("manager") }) {
            val log = KotlinLogging.logger {}
        }

        fun doStuff() {
            log.debug { "name=$name" }
        }
    }

    val appender = InMemoryAppender()

    @Test
    fun `get singleton manager`() {
        val manager = Manager.getInstance()
        manager.doStuff()

        appender.lastMessage!! shouldContain "name=manager"
    }

    @Test
    fun `get singleton manager with parallel mode`() {
        val managers = List(100) { it }
            .parallelStream()
            .map { Manager.getInstance() }.toList()

        managers.size shouldEqualTo 100
        managers.toSet().size shouldEqualTo 1
    }
}