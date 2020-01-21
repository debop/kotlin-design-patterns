package io.kommons.designpatterns.databus.members

import io.kommons.designpatterns.databus.data.MessageData
import io.kommons.designpatterns.databus.data.StartingData
import io.kommons.designpatterns.databus.data.StoppingData
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldContain
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class MessageCollectorMemberTest {
    @Test
    fun `collect message from message data`() {
        val message = "message"
        val messageData = MessageData(message)
        val collector = MessageCollectorMember("collector")

        collector.accept(messageData)

        collector.getMessages() shouldContain message
    }

    @Test
    fun `collect ignores message from other data types`() {
        val startingData = StartingData(LocalDateTime.now())
        val stoppingData = StoppingData(LocalDateTime.now())
        val collector = MessageCollectorMember("collector")

        collector.accept(startingData)
        collector.accept(stoppingData)

        collector.getMessages().shouldBeEmpty()
    }
}