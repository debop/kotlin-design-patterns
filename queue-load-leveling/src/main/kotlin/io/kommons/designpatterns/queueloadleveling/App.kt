package io.kommons.designpatterns.queueloadleveling

import io.kommons.logging.KotlinLogging
import io.kommons.logging.error
import io.kommons.logging.info
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class App

private val log = KotlinLogging.logger { }
private const val SHUTDOWN_TIME_SEC = 15L

fun main() {
    val executor = Executors.newFixedThreadPool(2)

    try {
        val msgQueue = MessageQueue()

        log.info { "Submitting TaskGenerators and ServiceExecutor thread." }

        val taskGenerator1 = TaskGenerator(msgQueue, 5)
        val taskGenerator2 = TaskGenerator(msgQueue, 1)
        val taskGenerator3 = TaskGenerator(msgQueue, 2)

        val serviceExecutor = ServiceExecutor(msgQueue)


        executor.submit { taskGenerator1.invoke() }
        executor.submit { taskGenerator2.invoke() }
        executor.submit { taskGenerator3.invoke() }

        // Submit service executor thread
        executor.submit { serviceExecutor.invoke() }

        log.info { "Initiating shutdown. Executor will shoutdown only after all the threads are completed" }

    } catch (e: Exception) {
        log.error(e) { "Fail to test." }
    } finally {
        executor.shutdown()
        if (!executor.awaitTermination(SHUTDOWN_TIME_SEC, TimeUnit.SECONDS)) {
            log.info { "Executor was shut down and exiting..." }
            executor.shutdownNow()
        }
    }
}