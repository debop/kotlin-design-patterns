package io.kommons.designpatterns.queueloadleveling

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

class MessageQueueTest {

    @Test
    fun `submit message to MessageQueeu`() {

        val expected = Message("Test Message")

        val msgQueue = MessageQueue()
        msgQueue.submit(expected)

        msgQueue.retrieve() shouldBeEqualTo expected
    }
}