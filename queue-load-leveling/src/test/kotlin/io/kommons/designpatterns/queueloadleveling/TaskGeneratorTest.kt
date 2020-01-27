package io.kommons.designpatterns.queueloadleveling

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Test
import kotlin.concurrent.thread

class TaskGeneratorTest {

    companion object: KLogging()

    @Test
    fun `run task generate and service executor`() {
        val msgQueue = MessageQueue()

        // Create a task generator thread with 1 job to submit
        val taskGenerator = TaskGenerator(msgQueue, 1)
        val taskGenerationThread = thread(start = true) { taskGenerator.invoke() }

        // Create a service executor thread
        val service = ServiceExecutor(msgQueue)
        val serviceExecutorThread = thread(start = true) { service.invoke() }

        taskGenerationThread.isAlive.shouldBeTrue()
        serviceExecutorThread.isAlive.shouldBeTrue()
    }
}