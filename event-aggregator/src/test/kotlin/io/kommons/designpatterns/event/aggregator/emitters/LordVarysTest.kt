package io.kommons.designpatterns.event.aggregator.emitters

import io.kommons.designpatterns.event.aggregator.Event
import io.kommons.designpatterns.event.aggregator.Weekday

/**
 * LoadVarysTest
 *
 * @author debop
 */
class LordVarysTest: AbstractEventEmitterTest<LoadVarys>(Weekday.SATURDAY,
                                                         Event.TRAITOR_DETECTED,
                                                         { LoadVarys(it) },
                                                         { LoadVarys() })