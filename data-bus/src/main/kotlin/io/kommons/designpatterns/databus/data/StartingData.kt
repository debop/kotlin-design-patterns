package io.kommons.designpatterns.databus.data

import io.kommons.designpatterns.databus.AbstractDataType
import io.kommons.logging.KLogging
import java.time.LocalDateTime

/**
 * StartingData
 *
 * @author debop
 */
class StartingData(val startedAt: LocalDateTime = LocalDateTime.now()): AbstractDataType() {

    companion object: KLogging() {
        fun of(startedAt: LocalDateTime) = StartingData(startedAt)
    }
}