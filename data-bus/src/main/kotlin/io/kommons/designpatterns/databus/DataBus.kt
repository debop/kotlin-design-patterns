package io.kommons.designpatterns.databus

import io.kommons.logging.KLogging

/**
 * DataBus
 *
 * @author debop
 */
class DataBus {

    companion object: KLogging() {
        private val INSTANCE: DataBus = DataBus()
        fun getInstance(): DataBus = INSTANCE
    }

    val listeners: MutableSet<Member> = hashSetOf()

    fun subscribe(member: Member) {
        this.listeners.add(member)
    }

    fun unsubscribe(member: Member) {
        this.listeners.remove(member)
    }

    fun publish(event: DataType) {
        event.dataBus = this
        listeners.forEach { it.accept(event) }
    }
}