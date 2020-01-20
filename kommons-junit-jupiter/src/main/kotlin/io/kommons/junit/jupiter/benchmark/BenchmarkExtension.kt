package io.kommons.junit.jupiter.benchmark

import io.kommons.junit.jupiter.store
import io.kommons.logging.KLogging
import org.junit.jupiter.api.extension.AfterTestExecutionCallback
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.util.concurrent.TimeUnit

/**
 * BenchmarkExtension
 *
 * @author debop
 */
class BenchmarkExtension(val unit: TimeUnit = TimeUnit.MILLISECONDS): BeforeTestExecutionCallback, AfterTestExecutionCallback {

    companion object: KLogging() {
        private const val REPORT_FORMAT = "Elapsed time in %s for %s"
    }

    override fun beforeTestExecution(context: ExtensionContext) {
        context.store(this.javaClass).put(context.requiredTestMethod, Stopwatch())
    }

    override fun afterTestExecution(context: ExtensionContext) {
        val testMethod = context.requiredTestMethod
        val stopwatch = context.store(this.javaClass).get(testMethod, Stopwatch::class.java)
        val duration = stopwatch.elapsedTime(unit)

        context.publishReportEntry(REPORT_FORMAT.format(unit.name, testMethod.name), duration.toString())
    }

    class Stopwatch {
        private val start: Long = System.nanoTime()

        fun elapsedTime(unit: TimeUnit): Long =
            unit.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS)
    }
}