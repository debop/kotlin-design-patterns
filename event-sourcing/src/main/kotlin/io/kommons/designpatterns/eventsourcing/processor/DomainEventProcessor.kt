package io.kommons.designpatterns.eventsourcing.processor

import io.kommons.designpatterns.eventsourcing.event.DomainEvent
import io.kommons.logging.KLogging

/**
 * DomainEventProcessor
 *
 * @author debop
 * @since 19. 9. 18.
 */
class DomainEventProcessor {

    companion object: KLogging()

    private val processorJournal = JsonFileJournal()

    fun process(domainEvent: DomainEvent) {
        domainEvent.process()
        processorJournal.write(domainEvent)
    }

    fun reset() {
        processorJournal.reset()
    }

    fun recover() {
        do {
            val domainEvent = processorJournal.readNext()
            domainEvent?.process()
        } while (domainEvent != null)
    }
}