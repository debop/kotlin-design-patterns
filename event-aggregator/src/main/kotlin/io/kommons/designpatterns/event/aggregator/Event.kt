package io.kommons.designpatterns.event.aggregator

/**
 * Event
 *
 * @author debop
 */
enum class Event(val description: String) {

    STARK_SIGHTED("Stark sighted"),
    WARSHIPS_APPROACHING("Warships approaching"),
    TRAITOR_DETECTED("Traitor detected");

    override fun toString(): String = description

}