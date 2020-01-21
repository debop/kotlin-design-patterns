package io.kommons.designpatterns.event.aggregator

import io.kommons.logging.KLogging

/**
 * KingsHand observes events from multiple sources and delivers them to listeners.
 *
 * @author debop
 */
class KingsHand(vararg obs: EventObserver): EventEmitter(*obs), EventObserver {

    companion object: KLogging()

    override fun onEvent(evt: Event?) {
        notifyObservers(evt)
    }

    override fun timePasses(day: Weekday) {
        // NOP
    }
}