package io.kommons.designpatterns.poisonpill

import io.kommons.designpatterns.poisonpill.Message.Headers
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.jupiter.api.Test

class SimpleMessageTest {

    companion object: KLogging()

    @Test
    fun `get headers`() {
        val message = SimpleMessage()
        message.getHeaders().shouldBeEmpty()

        val senderName = "test"
        message.addHeader(Headers.SENDER, senderName)
        message.getHeaders().shouldNotBeEmpty()
        message.getHeader(Headers.SENDER) shouldEqual senderName
    }
}