package io.kommons.junit.jupiter.utils

import io.kommons.junit.jupiter.utils.ExecutionEvent.EventType.DYNAMIC_TEST_REGISTERED
import io.kommons.junit.jupiter.utils.ExecutionEvent.EventType.FINISHED
import io.kommons.junit.jupiter.utils.ExecutionEvent.EventType.REPORTING_ENTRY_PUBLISHED
import io.kommons.junit.jupiter.utils.ExecutionEvent.EventType.SKIPPED
import io.kommons.junit.jupiter.utils.ExecutionEvent.EventType.STARTED
import io.kommons.logging.KLogging
import io.kommons.logging.error
import io.kommons.logging.trace
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.engine.TestExecutionResult
import org.junit.platform.engine.reporting.ReportEntry
import kotlin.reflect.KClass

/**
 * ExecutionEvent
 *
 * @author debop
 */
class ExecutionEvent(val type: EventType,
                     val testDescriptor: TestDescriptor,
                     private val payload: Any? = null) {

    enum class EventType {
        DYNAMIC_TEST_REGISTERED,
        SKIPPED,
        STARTED,
        FINISHED,
        REPORTING_ENTRY_PUBLISHED
    }

    fun <T: Any> getPayload(payloadClass: Class<T>): T? {
        return if (payload != null && payloadClass.isInstance(payload)) payloadClass.cast(payload)
        else null
    }

    override fun toString(): String =
        "ExecutionEvent(type=$type, testDescriptor=$testDescriptor, payload=$payload"


    companion object: KLogging() {

        fun reportingEntryPublished(testDescriptor: TestDescriptor, entry: ReportEntry): ExecutionEvent {
            log.trace { "reporting entry published. entry=$entry" }
            return ExecutionEvent(REPORTING_ENTRY_PUBLISHED, testDescriptor, entry)
        }

        fun dynamicTestRegistered(testDescriptor: TestDescriptor): ExecutionEvent {
            log.trace { "dynamic test registered. testDescriptor=$testDescriptor" }
            return ExecutionEvent(DYNAMIC_TEST_REGISTERED, testDescriptor)
        }

        fun executionSkipped(testDescriptor: TestDescriptor, reason: String? = null): ExecutionEvent {
            log.trace { "execution skipped. reason=$reason" }
            return ExecutionEvent(SKIPPED, testDescriptor, reason)
        }

        fun executionStarted(testDescriptor: TestDescriptor): ExecutionEvent {
            log.trace { "execution is started. testDescriptor=$testDescriptor" }
            return ExecutionEvent(STARTED, testDescriptor, null)
        }

        fun executionFinished(testDescriptor: TestDescriptor, result: TestExecutionResult): ExecutionEvent {
            if (result.throwable.isPresent) {
                log.error(result.throwable.get()) { "execution is failed. status=${result.status}" }
            } else {
                log.trace { "execution is finished. testDescriptor=$testDescriptor, result=$result" }
            }
            return ExecutionEvent(FINISHED, testDescriptor, result)
        }

        fun byEventType(type: EventType) = { evt: ExecutionEvent -> evt.type == type }

        fun byTestDescriptor(predicate: (TestDescriptor) -> Boolean) =
            { evt: ExecutionEvent -> predicate.invoke(evt.testDescriptor) }

        fun <T: Any> byPayload(payloadClass: KClass<T>, predicate: (T) -> Boolean) =
            { evt: ExecutionEvent -> evt.getPayload(payloadClass.java)?.run { predicate(this) } ?: false }
    }
}