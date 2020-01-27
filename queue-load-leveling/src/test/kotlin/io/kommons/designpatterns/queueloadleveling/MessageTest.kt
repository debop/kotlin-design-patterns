package io.kommons.designpatterns.queueloadleveling

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class MessageTest {

    @Test
    fun `create message`() {
        val msg = "Test Message"
        val message = Message(msg)

        message.msg shouldEqual msg
    }
}