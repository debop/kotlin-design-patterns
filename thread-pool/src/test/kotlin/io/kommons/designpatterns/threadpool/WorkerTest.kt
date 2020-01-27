package io.kommons.designpatterns.threadpool

import io.kommons.logging.KLogging
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class WorkerTest {

    companion object: KLogging()

    @Test
    fun `run worker`() {
        val task = mockk<Task>(relaxUnitFun = true)
        every { task.timeMs } returns 100

        val worker = Worker(task)
        confirmVerified(task)

        worker.invoke()
        verify(exactly = 1) { task.timeMs }
        verify(exactly = 1) { task.toString() }
        confirmVerified(task)
    }
}