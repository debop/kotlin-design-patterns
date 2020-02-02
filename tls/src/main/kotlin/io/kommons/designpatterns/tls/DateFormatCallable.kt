package io.kommons.designpatterns.tls

import io.kommons.logging.KLogging
import io.kommons.logging.info
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.concurrent.Callable

class DateFormatCallable(inDateFormat: String,
                         private val dateValue: String): Callable<Result> {

    companion object: KLogging()

    private val df: ThreadLocal<DateFormat> = ThreadLocal.withInitial { SimpleDateFormat(inDateFormat) }

    override fun call(): Result {
        log.info { "${Thread.currentThread()} started executing..." }

        val result = Result()

        repeat(5) { i ->
            try {
                // this is the statement where it is important to have the
                // instance of SimpleDateFormat locally
                // Create the date value and store it in dateList
                result.dateList += this.df.get().parse(this.dateValue)
            } catch (e: Exception) {
                result.exceptionList += "${e.javaClass}: ${e.message}"
            }
        }

        log.info { "${Thread.currentThread()} finished processing part of the thread" }

        return result
    }
}