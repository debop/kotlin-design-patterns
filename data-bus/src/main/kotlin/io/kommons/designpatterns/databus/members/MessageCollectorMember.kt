package io.kommons.designpatterns.databus.members

import io.kommons.designpatterns.databus.DataType
import io.kommons.designpatterns.databus.Member
import io.kommons.designpatterns.databus.data.MessageData
import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * MessageCollectorMember
 *
 * @author debop
 */
class MessageCollectorMember(val name: String): Member {

    companion object: KLogging()

    private val messages: MutableList<String> = mutableListOf()


    override fun accept(data: DataType) {
        if (data is MessageData) {
            handleEvent(data)
        }
    }

    private fun handleEvent(data: MessageData) {
        log.info { "$name sees message ${data.message}" }
        messages.add(data.message)
    }

    fun getMessages(): List<String> = messages

}