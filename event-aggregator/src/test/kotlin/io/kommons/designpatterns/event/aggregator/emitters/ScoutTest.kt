package io.kommons.designpatterns.event.aggregator.emitters

import io.kommons.designpatterns.event.aggregator.Event
import io.kommons.designpatterns.event.aggregator.Weekday

class ScoutTest: AbstractEventEmitterTest<Scout>(Weekday.TUESDAY,
                                                 Event.WARSHIPS_APPROACHING,
                                                 { observer -> Scout(observer) },
                                                 { Scout() })