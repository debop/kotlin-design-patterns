package io.kommons.designpatterns.databus.members

import io.kommons.designpatterns.databus.DataType
import io.kommons.designpatterns.databus.Member
import io.kommons.designpatterns.databus.data.MessageData
import io.kommons.designpatterns.databus.data.StartingData
import io.kommons.designpatterns.databus.data.StoppingData
import io.kommons.logging.KLogging
import io.kommons.logging.info
import java.time.LocalDateTime

/**
 * StatusMember
 *
 * @author debop
 */
class StatusMember(val id: Int): Member {

    companion object: KLogging()

    private var startedAt: LocalDateTime? = null
    private var stoppedAt: LocalDateTime? = null

    val started: LocalDateTime? get() = startedAt
    val stopped: LocalDateTime? get() = stoppedAt

    override fun accept(data: DataType) {
        when (data) {
            is StartingData -> handleEvent(data)
            is StoppingData -> handleEvent(data)
        }
    }

    private fun handleEvent(data: StartingData) {
        startedAt = data.startedAt
        log.info { "Receiver $id sees application started at $startedAt" }
    }

    private fun handleEvent(data: StoppingData) {
        stoppedAt = data.stoppedAt
        log.info { "Receiver $id sees application stopping at $stoppedAt" }
        log.info { "Receiver $id sending goodbye message" }

        data.dataBus.publish(MessageData.of("Goodbye cruel world from $id"))
    }
}