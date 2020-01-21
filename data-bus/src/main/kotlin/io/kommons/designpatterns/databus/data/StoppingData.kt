package io.kommons.designpatterns.databus.data

import io.kommons.designpatterns.databus.AbstractDataType
import io.kommons.logging.KLogging
import java.time.LocalDateTime

/**
 * StoppingData
 *
 * @author debop
 */
class StoppingData(val stoppedAt: LocalDateTime): AbstractDataType() {

    companion object: KLogging() {
        fun of(stoppedAt: LocalDateTime) = StoppingData(stoppedAt)
    }
}