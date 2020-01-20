package io.kommons.junit.jupiter.benchmark

import io.kommons.junit.jupiter.utils.ExecutionEvent
import io.kommons.junit.jupiter.utils.RecordingExecutionListener
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.engine.JupiterTestEngine
import org.junit.platform.engine.ExecutionRequest
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.discovery.DiscoverySelectors
import org.junit.platform.engine.reporting.ReportEntry
import org.junit.platform.launcher.LauncherDiscoveryRequest
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder
import java.util.concurrent.TimeUnit

class BenchmarkExtensionTest {

    companion object: KLogging()

    private lateinit var engine: JupiterTestEngine
    private lateinit var listener: RecordingExecutionListener

    @BeforeEach
    fun beforeEach() {
        engine = JupiterTestEngine()
        listener = RecordingExecutionListener()
    }

    @Test
    fun `기본 시각 단위로 Benchmark 결과를 출력`() {
        // when executing a test case
        execute(DefaultTimeUnitBenchmarkTest::class.java)

        // then the benchmark report event(s) are published
        val publishedEvents = getReportEntries()

        publishedEvents.size shouldEqualTo 1
        toReportEntryKey(publishedEvents[0]) shouldEqual "Elapsed time in %s for canBenchmark()".format(TimeUnit.MILLISECONDS.name)
    }

    @Test
    fun `사용자 정의 시각 단위로 Benchmark 결과를 출력`() {
        // when executing a test case
        execute(CustomTimeUnitBenchmarkTest::class.java)

        // then the benchmark report event(s) are published
        val publishedEvents = getReportEntries()

        publishedEvents.size shouldEqualTo 1
        toReportEntryKey(publishedEvents[0]) shouldEqual "Elapsed time in %s for canBenchmark()".format(TimeUnit.MICROSECONDS.name)
    }

    private fun execute(clazz: Class<*>) {
        val request = getRequest(clazz)
        engine.execute(ExecutionRequest(getTestDescriptor(request), listener, request.configurationParameters))
    }

    private fun getRequest(clazz: Class<*>): LauncherDiscoveryRequest {
        return LauncherDiscoveryRequestBuilder.request().selectors(DiscoverySelectors.selectClass(clazz)).build()
    }

    private fun getTestDescriptor(request: LauncherDiscoveryRequest): TestDescriptor {
        return engine.discover(request, UniqueId.forEngine(engine.id))
    }

    private fun getReportEntries(): List<ExecutionEvent> {
        return listener
            .getEventsByType(ExecutionEvent.EventType.REPORTING_ENTRY_PUBLISHED)
            .toList()
    }

    private fun toReportEntryKey(event: ExecutionEvent): String {
        return event.getPayload(ReportEntry::class.java)?.keyValuePairs?.keys?.iterator()?.next() + "()"
    }

    @ExtendWith(BenchmarkExtension::class)
    class DefaultTimeUnitBenchmarkTest {
        @Test
        fun canBenchmark() {
            // note: the actual assertion - verifying publication of report events - is performed in the containing class
            Thread.sleep(5)
        }
    }

    class CustomTimeUnitBenchmarkTest {
        companion object {
            @RegisterExtension
            @JvmField
            val benchmarkExtension = BenchmarkExtension(TimeUnit.MICROSECONDS)
        }

        @Test
        fun canBenchmark() {
            // note: the actual assertion - verifying publication of report events - is performed in the containing class
            Thread.sleep(5)
        }
    }
}