package io.kommons.designpatterns.poisonpill

import io.kommons.designpatterns.poisonpill.Message.Headers
import io.kommons.junit.jupiter.output.InMemoryAppender
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldContain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ConsumerTest {

    companion object: KLogging() {
        fun createMessage(sender: String, body: String): SimpleMessage =
            SimpleMessage().also {
                it.addHeader(Headers.SENDER, sender)
                it.addHeader(Headers.DATE, LocalDateTime.now().toString())
                it.body = body
            }
    }

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setup() {
        appender = InMemoryAppender(Consumer::class)
    }

    @AfterEach
    fun cleanup() {
        appender.stop()
    }

    @Test
    fun `test consumer`() {
        val messages = listOf(createMessage("you", "Hello!"),
                              createMessage("me", "Hi!"),
                              Message.POISON_PILL,
                              createMessage("late_for_the_party", "Hello? Anyone here?"))

        val queue = SimpleMessageQueue(messages.size)
        messages.forEach {
            queue.put(it)
        }

        Consumer("NSA", queue).consume()

        appender.messages shouldContain "Message [Hello!] from [you] received by [NSA]"
        appender.messages shouldContain "Message [Hi!] from [me] received by [NSA]"
        appender.messages shouldContain "Consumer NSA receive request to terminate."

    }


}