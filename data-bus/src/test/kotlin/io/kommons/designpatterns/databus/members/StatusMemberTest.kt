package io.kommons.designpatterns.databus.members

import io.kommons.designpatterns.databus.data.MessageData
import io.kommons.designpatterns.databus.data.StartingData
import io.kommons.designpatterns.databus.data.StoppingData
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class StatusMemberTest {

    @Test
    fun `status records the start time`() {
        val startTime = LocalDateTime.of(2019, Month.NOVEMBER, 11, 19, 9)
        val startingData = StartingData(startTime)
        val statusMember = StatusMember(1)

        statusMember.accept(startingData)

        statusMember.started shouldEqual startTime
    }

    @Test
    fun `status records the stop time`() {
        val stoppingTime = LocalDateTime.of(2019, Month.NOVEMBER, 11, 19, 12)
        val stoppingData = StoppingData(stoppingTime)
        val statusMember = StatusMember(1)

        statusMember.accept(stoppingData)

        statusMember.stopped shouldEqual stoppingTime
    }

    @Test
    fun `status ignore message data`() {
        val messageData = MessageData("message")
        val statusMember = StatusMember(1)

        statusMember.accept(messageData)

        statusMember.started.shouldBeNull()
        statusMember.stopped.shouldBeNull()
    }
}