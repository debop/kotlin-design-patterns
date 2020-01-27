package io.kommons.designpatterns.poisonpill

import io.kommons.designpatterns.poisonpill.Message.Headers
import io.kommons.logging.KLogging
import io.mockk.clearMocks
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class ProducerTest {

    companion object: KLogging()

    val publishPoint = mockk<MqPublishPoint>(relaxUnitFun = true)

    @BeforeEach
    fun setup() {
        clearMocks(publishPoint)
    }


    @Test
    fun `send message`() {
        val producer = Producer("producer", publishPoint)
        confirmVerified(publishPoint)

        producer.send("Hello!")
        val slot = slot<Message>()

        verify(exactly = 1) { publishPoint.put(capture(slot)) }

        val message = slot.captured
        message.shouldNotBeNull()
        message.getHeader(Headers.SENDER) shouldEqual "producer"
        message.body shouldEqual "Hello!"

        confirmVerified(publishPoint)
    }

    @Test
    fun `stop producer`() {
        val producer = Producer("producer", publishPoint)
        confirmVerified(publishPoint)

        producer.stop()
        verify(exactly = 1) { publishPoint.put(eq(Message.POISON_PILL)) }

        try {
            producer.send("Hello!")
            fail { "Expected 'IllegalStateException' at this point, since the producer has stopped!" }
        } catch (e: IllegalStateException) {
            e.shouldNotBeNull()
            e.localizedMessage.shouldNotBeNull()
            e.localizedMessage shouldEqual "Producer producer was stopped and fail to deliver requested message [Hello!]."
        }

        confirmVerified(publishPoint)
    }
}