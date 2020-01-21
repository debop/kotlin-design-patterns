package io.kommons.designpatterns.event.aggregator.emitters

import io.kommons.designpatterns.event.aggregator.Event
import io.kommons.designpatterns.event.aggregator.EventEmitter
import io.kommons.designpatterns.event.aggregator.EventObserver
import io.kommons.designpatterns.event.aggregator.Weekday

/**
 * LordVarys produces events.
 */
class LoadVarys(vararg observers: EventObserver): EventEmitter(*observers) {

    override fun timePasses(day: Weekday) {
        if (day == Weekday.SATURDAY) {
            notifyObservers(Event.TRAITOR_DETECTED)
        }
    }

}