package io.kommons.designpatterns.eventsourcing.event

import java.io.Serializable

/**
 * DomainEvent
 *
 * @author debop
 * @since 19. 9. 18.
 */
interface DomainEvent: Serializable {

    val sequenceId: Long

    val createdTime: Long

    val eventClassName: String

    var isRealTime: Boolean

    fun process()

}