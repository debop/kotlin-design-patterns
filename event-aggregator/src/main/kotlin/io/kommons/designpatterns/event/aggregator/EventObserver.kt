package io.kommons.designpatterns.event.aggregator

/**
 * Observers of events
 *
 * @author debop
 */
interface EventObserver {

    fun onEvent(evt: Event?)

}