package io.kommons.designpatterns.tls

import io.kommons.logging.KotlinLogging
import io.kommons.logging.info
import java.util.Calendar
import java.util.concurrent.Executors

class App

private val log = KotlinLogging.logger { }

fun main() {
    var counterDateValues = 0
    var counterExceptions = 0

    val callableDf = DateFormatCallable("yyyy-MM-dd", "2020-01-15")
    val executor = Executors.newCachedThreadPool()

    val futureResults = listOf(
        executor.submit(callableDf),
        executor.submit(callableDf),
        executor.submit(callableDf),
        executor.submit(callableDf)
    )

    try {
        val results = futureResults.map { it.get() }

        results.forEach { result ->
            counterDateValues += printAndCountDates(result)
            counterExceptions += printAndCountException(result)
        }

        log.info { "The List dateList contains $counterDateValues date values" }
        log.info { "The List exceptionList contains $counterExceptions exceptions" }

    } catch (e: Exception) {
        log.info(e) { "Abnormal end of program." }
    }
    executor.shutdown()
}

private fun printAndCountDates(result: Result): Int {
    val counter = result.dateList.size
    result.dateList.forEach { dt ->
        val cal = Calendar.getInstance()
        cal.time = dt
        log.info { "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH) + 1}-${cal.get(Calendar.DAY_OF_MONTH)}" }
    }

    return counter
}

private fun printAndCountException(result: Result): Int {
    val counter = result.exceptionList.size
    result.exceptionList.forEach {
        log.info { it }
    }
    return counter
}