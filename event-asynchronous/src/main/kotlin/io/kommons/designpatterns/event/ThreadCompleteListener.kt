package io.kommons.designpatterns.event

/**
 * Interface with listener behaviour related to Thread Completion.
 *
 * @author debop
 */
interface ThreadCompleteListener {

    fun completedEventHandler(eventId: Int)

}