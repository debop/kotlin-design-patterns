package io.kommons.designpatterns.decorator

import io.kommons.junit.jupiter.output.InMemoryAppender
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldNotBeNullOrBlank
import org.junit.jupiter.api.Test

/**
 * DecorateTest
 */
class SimpleTrollTest {

    val appender = InMemoryAppender(SimpleTroll::class)

    @Test
    fun `run simple troll`() {
        val troll = SimpleTroll()

        troll.attach()
        appender.lastMessage.shouldNotBeNullOrBlank() shouldContain "The troll tries to grab you!"

        troll.fleeBattle()
        appender.lastMessage.shouldNotBeNullOrBlank() shouldContain "The troll shrieks in horror and runs away!"
    }
}