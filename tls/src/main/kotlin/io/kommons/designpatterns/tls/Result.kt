package io.kommons.designpatterns.tls

import java.util.Date

/**
 * Result object that will be returned by the Callable [DateFormatCallable] used in [App]
 *
 */
class Result {

    // A list to collect the date values created in one thread
    val dateList = mutableListOf<Date>()

    // A list to collect Exceptions thrown in one threads (should be none in this example)
    val exceptionList = mutableListOf<String>()

}