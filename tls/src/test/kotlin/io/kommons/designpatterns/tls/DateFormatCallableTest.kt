package io.kommons.designpatterns.tls

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.util.Calendar
import java.util.concurrent.Executors

class DateFormatCallableTest {

    companion object: KLogging()

    val callableDf = DateFormatCallable("yyyy-MM-dd", "2020-10-14")

    @Test
    fun `run callable once`() {
        val executor = Executors.newCachedThreadPool()
        val futureResult = executor.submit(callableDf)

        try {
            val result = futureResult.get()
            result.dateList.size shouldEqualTo 5
            result.exceptionList.size shouldEqualTo 0

            val createdDateValues = convertDatesToString(result)
            createdDateValues shouldEqual List(5) { "2020-10-14" }
        } catch (e: Exception) {
            fail { "Setup failed: $e" }
        }

        executor.shutdown()
    }

    @Test
    fun `run callable multiple in multithreads`() {

        val executor = Executors.newCachedThreadPool()
        val futureResults = listOf(
            executor.submit(callableDf),
            executor.submit(callableDf),
            executor.submit(callableDf),
            executor.submit(callableDf),
            executor.submit(callableDf)
        )

        try {
            val results = futureResults.map { it.get() }

            results.forEach { result ->
                result.dateList.size shouldEqualTo 5
                result.exceptionList.size shouldEqualTo 0

                val createdDateValues = convertDatesToString(result)
                createdDateValues shouldEqual List(5) { "2020-10-14" }
            }
        } catch (e: Exception) {
            fail { "Setup failed: $e" }
        }

        executor.shutdown()
    }

    @Test
    fun `run invalid date format`() {
        val callableDf = DateFormatCallable("yyyy-MM-dd", "2020.10.14")

        val executor = Executors.newCachedThreadPool()
        val futureResult = executor.submit(callableDf)

        try {
            val result = futureResult.get()
            result.dateList.size shouldEqualTo 0
            result.exceptionList.size shouldEqualTo 5

            result.exceptionList.forEach { exception ->
                exception shouldEqual """class java.text.ParseException: Unparseable date: "2020.10.14""""
            }
        } catch (e: Exception) {
            fail { "Setup failed: $e" }
        }

        executor.shutdown()
    }

    private fun convertDatesToString(result: Result): List<String> {
        return result.dateList.map { dt ->
            val cal = Calendar.getInstance()
            cal.time = dt
            "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH) + 1}-${cal.get(Calendar.DAY_OF_MONTH)}"
        }
    }

}
