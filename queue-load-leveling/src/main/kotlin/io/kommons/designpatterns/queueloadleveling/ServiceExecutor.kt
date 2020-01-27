package io.kommons.designpatterns.queueloadleveling

import io.kommons.logging.KLogging
import io.kommons.logging.info

class ServiceExecutor(private val queue: MessageQueue): () -> Unit {

    companion object: KLogging()

    override fun invoke() {
        try {
            while (!Thread.currentThread().isInterrupted) {
                val msg = queue.retrieveMsg()

                msg?.let { log.info { "$it is sserved" } }
                ?: log.info { "Service Executor: Waiting for Messages to serve .." }

                Thread.sleep(1000)
            }
        } catch (e: Exception) {
            log.error(e.message)
        }
    }
}