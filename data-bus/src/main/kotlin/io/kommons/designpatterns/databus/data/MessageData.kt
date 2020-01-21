package io.kommons.designpatterns.databus.data

import io.kommons.designpatterns.databus.AbstractDataType
import io.kommons.logging.KLogging

/**
 * MessageData
 *
 * @author debop
 */
class MessageData(val message: String): AbstractDataType() {

    companion object: KLogging() {
        fun of(message: String) = MessageData(message)
    }
}