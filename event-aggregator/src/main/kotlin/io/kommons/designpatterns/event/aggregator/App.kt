package io.kommons.designpatterns.event.aggregator

import io.kommons.designpatterns.event.aggregator.emitters.LoadVarys
import io.kommons.designpatterns.event.aggregator.emitters.LordBaelish
import io.kommons.designpatterns.event.aggregator.emitters.Scout
import io.kommons.designpatterns.event.aggregator.observers.KingJoffrey

class App

fun main() {

    val kingJoffrey = KingJoffrey()
    val kingsHand = KingsHand(kingJoffrey)

    val emitters: List<EventEmitter> = listOf(kingsHand,
                                              LordBaelish(kingsHand),
                                              LoadVarys(kingsHand),
                                              Scout(kingsHand))

    Weekday.values().forEach { weekday ->
        emitters.forEach { emitter ->
            emitter.timePasses(weekday)
        }
    }
}