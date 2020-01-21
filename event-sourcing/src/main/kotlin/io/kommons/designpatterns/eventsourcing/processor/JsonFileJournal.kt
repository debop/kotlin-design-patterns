package io.kommons.designpatterns.eventsourcing.processor

import com.google.gson.Gson
import com.google.gson.JsonParser
import io.kommons.designpatterns.eventsourcing.event.AccountCreateEvent
import io.kommons.designpatterns.eventsourcing.event.DomainEvent
import io.kommons.designpatterns.eventsourcing.event.MoneyDepositEvent
import io.kommons.designpatterns.eventsourcing.event.MoneyTransferEvent
import io.kommons.logging.KLogging
import io.kommons.logging.debug
import java.io.File
import java.io.FileOutputStream

class JsonFileJournal {

    companion object: KLogging()

    private val file: File = File("Journal.json")
    private val events = mutableListOf<String>()
    private var index: Int = 0

    private val gson: Gson = Gson()
    private val parser = JsonParser()

    init {
        if (file.exists()) {
            file.inputStream().reader(Charsets.UTF_8).buffered().useLines { lines ->
                lines.forEach { events.add(it) }
            }
        } else {
            reset()
        }
    }

    /**
     * Write event to file as JSON
     *
     * @param domainEvent domain event
     */
    fun write(domainEvent: DomainEvent) {
        val jsonElement = when (domainEvent) {
            is AccountCreateEvent -> gson.toJsonTree(domainEvent, AccountCreateEvent::class.java)
            is MoneyDepositEvent  -> gson.toJsonTree(domainEvent, MoneyDepositEvent::class.java)
            is MoneyTransferEvent -> gson.toJsonTree(domainEvent, MoneyTransferEvent::class.java)
            else                  -> error("Journal Event not recognized. domainEvent=${domainEvent.javaClass}")
        }

        FileOutputStream(file, true).bufferedWriter(Charsets.UTF_8).use { output ->
            val eventString = jsonElement.toString()
            log.debug { "Write event. $eventString" }

            output.write(eventString)
            output.newLine()
        }
    }

    /**
     * Reset
     */
    fun reset() {
        file.delete()
    }

    /**
     * Read next domain event.
     *
     * @return domain event
     */
    fun readNext(): DomainEvent? {
        if (index >= events.size) {
            return null
        }

        val event = events.get(index)
        index++

        parser.parse(event)
        val jsonElement = parser.parse(event)
        val eventClassName = jsonElement.asJsonObject.get("eventClassName").asString

        val domainEvent = when (eventClassName) {
            AccountCreateEvent::class.java.simpleName -> gson.fromJson(jsonElement, AccountCreateEvent::class.java)
            MoneyDepositEvent::class.java.simpleName  -> gson.fromJson(jsonElement, MoneyDepositEvent::class.java)
            MoneyTransferEvent::class.java.simpleName -> gson.fromJson(jsonElement, MoneyTransferEvent::class.java)
            else                                      -> error("Journal event not recognized")
        }

        domainEvent.isRealTime = false
        return domainEvent
    }
}