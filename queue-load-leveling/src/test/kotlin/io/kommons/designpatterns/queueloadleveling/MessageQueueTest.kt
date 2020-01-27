package io.kommons.designpatterns.queueloadleveling

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class MessageQueueTest {

    @Test
    fun `submit message to MessageQueeu`() {

        val expected = Message("Test Message")

        val msgQueue = MessageQueue()
        msgQueue.submitMsg(expected)

        msgQueue.retrieveMsg() shouldEqual expected
    }
}