package io.kommons.designpatterns.threadpool

import io.kommons.logging.KLogging
import io.kommons.logging.error
import io.kommons.logging.info

class Worker(private val task: Task): () -> Unit {

    companion object: KLogging()

    override fun invoke() {
        log.info { "${Thread.currentThread().name} processing $task" }
        try {
            Thread.sleep(task.timeMs.toLong())
        } catch (e: InterruptedException) {
            log.error(e) { "Fail to run task." }
        }
    }
}