package io.kommons.designpatterns.event.aggregator.emitters

import io.kommons.designpatterns.event.aggregator.Event.WARSHIPS_APPROACHING
import io.kommons.designpatterns.event.aggregator.EventEmitter
import io.kommons.designpatterns.event.aggregator.EventObserver
import io.kommons.designpatterns.event.aggregator.Weekday
import io.kommons.designpatterns.event.aggregator.Weekday.TUESDAY

/**
 * Scout produces events
 */
class Scout(vararg obs: EventObserver): EventEmitter(*obs) {

    override fun timePasses(day: Weekday) {
        if (day == TUESDAY) {
            notifyObservers(WARSHIPS_APPROACHING)
        }
    }
}