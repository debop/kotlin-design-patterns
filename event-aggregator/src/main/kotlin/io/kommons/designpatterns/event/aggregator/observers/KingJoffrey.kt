package io.kommons.designpatterns.event.aggregator.observers

import io.kommons.designpatterns.event.aggregator.Event
import io.kommons.designpatterns.event.aggregator.EventObserver
import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * KingJoffrey observes events from [KingsHand].
 *
 * @author debop
 */
class KingJoffrey: EventObserver {

    companion object: KLogging()

    override fun onEvent(evt: Event?) {
        log.info { "Received event from the King's Hand: $evt" }
    }
}