package io.kommons.designpatterns.queueloadleveling

import io.kommons.logging.KLogging
import io.kommons.logging.error
import io.kommons.logging.info

class TaskGenerator(private val msgQueue: MessageQueue,
                    private val msgCount: Int): Task, () -> Unit {

    companion object: KLogging()

    override fun submit(msg: Message) {
        try {
            this.msgQueue.submit(msg)
        } catch (e: Exception) {
            log.error(e) { "Fail to submit message." }
        }
    }

    override fun invoke() {
        var count = msgCount

        try {
            while (count > 0) {
                val statusMsg = "Message-$count submitted by ${Thread.currentThread().name}"
                this.submit(Message(statusMsg))

                log.info { "Submit:  $statusMsg" }

                count--
                Thread.sleep(1000)
            }
        } catch (e: Exception) {
            log.error(e) { "Fail to run task generate" }
        }
    }
}