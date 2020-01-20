package io.kommons.junit.jupiter.utils

import org.junit.platform.engine.EngineExecutionListener
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.engine.TestExecutionResult
import org.junit.platform.engine.reporting.ReportEntry
import java.util.concurrent.CopyOnWriteArrayList

/**
 * 테스트 시 발생하는 모든 이벤트를 기록하는 [EngineExecutionListener]를 구현합니다.
 *
 * @author debop
 */
class RecordingExecutionListener: EngineExecutionListener {

    private val events = CopyOnWriteArrayList<ExecutionEvent>()

    override fun dynamicTestRegistered(testDescriptor: TestDescriptor) {
        addEvent(ExecutionEvent.dynamicTestRegistered(testDescriptor))
    }

    override fun executionSkipped(testDescriptor: TestDescriptor, reason: String?) {
        addEvent(ExecutionEvent.executionSkipped(testDescriptor, reason))
    }

    override fun executionStarted(testDescriptor: TestDescriptor) {
        addEvent(ExecutionEvent.executionStarted(testDescriptor))
    }

    override fun executionFinished(testDescriptor: TestDescriptor, testExecutionResult: TestExecutionResult) {
        addEvent(ExecutionEvent.executionFinished(testDescriptor, testExecutionResult))
    }

    override fun reportingEntryPublished(testDescriptor: TestDescriptor, entry: ReportEntry) {
        addEvent(ExecutionEvent.reportingEntryPublished(testDescriptor, entry))
    }

    fun getEventsByType(type: ExecutionEvent.EventType): Sequence<ExecutionEvent> {
        return events.asSequence().filter { ExecutionEvent.byEventType(type).invoke(it) }
    }

    fun getEventsByTypeAndTestDescriptor(type: ExecutionEvent.EventType,
                                         predicate: (TestDescriptor) -> Boolean): Sequence<ExecutionEvent> {
        return events.asSequence()
            .filter {
                ExecutionEvent.byEventType(type).invoke(it) &&
                ExecutionEvent.byTestDescriptor(predicate).invoke(it)
            }
    }

    fun countEventsByType(type: ExecutionEvent.EventType): Int = getEventsByType(type).count()

    fun getFinishedEventsByStatus(status: TestExecutionResult.Status): Sequence<ExecutionEvent> {
        return getEventsByType(ExecutionEvent.EventType.FINISHED)
            .filter { evt ->
                ExecutionEvent.byPayload(TestExecutionResult::class) { it.status == status }.invoke(evt)
            }
    }

    private fun addEvent(event: ExecutionEvent) {
        events.add(event)
    }
}