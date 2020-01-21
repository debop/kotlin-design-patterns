package io.kommons.designpatterns.eventsourcing.event

/**
 * AbstractDomainEvent
 *
 * @author debop
 * @since 19. 9. 18.
 */
abstract class AbstractDomainEvent(override val sequenceId: Long,
                                   override val createdTime: Long,
                                   override val eventClassName: String): DomainEvent {

    override var isRealTime: Boolean = true

}