package io.kommons.designpatterns.event.aggregator

/**
 * EventEmitter is the base class for event producers that can be observed.
 *
 * @author debop
 */
abstract class EventEmitter(vararg observers: EventObserver) {

    private val observers: MutableList<EventObserver> = observers.toMutableList()

    fun registerObserver(obs: EventObserver) {
        observers.add(obs)
    }

    protected fun notifyObservers(e: Event?) {
        observers.forEach { observer ->
            observer.onEvent(e)
        }
    }

    abstract fun timePasses(day: Weekday)
}