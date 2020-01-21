package io.kommons.designpatterns.event.aggregator.emitters

import io.kommons.designpatterns.event.aggregator.Event
import io.kommons.designpatterns.event.aggregator.Weekday

/**
 * LoardBaelishTest
 *
 * @author debop
 */
class LordBaelishTest: AbstractEventEmitterTest<LordBaelish>(Weekday.FRIDAY,
                                                             Event.STARK_SIGHTED,
                                                             { LordBaelish(it) },
                                                             { LordBaelish() })