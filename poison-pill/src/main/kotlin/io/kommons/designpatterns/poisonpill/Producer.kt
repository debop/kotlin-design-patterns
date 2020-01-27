package io.kommons.designpatterns.poisonpill

import io.kommons.designpatterns.poisonpill.Message.Headers
import io.kommons.logging.KLogging
import io.kommons.logging.error
import java.time.LocalDateTime

class Producer(private val name: String, private val queue: MqPublishPoint) {

    companion object: KLogging()

    private var isStopped: Boolean = false

    fun send(body: String) {
        if (isStopped) {
            error("Producer $name was stopped and fail to deliver requested message [$body].")
        }

        val msg = SimpleMessage().also {
            it.addHeader(Headers.DATE, LocalDateTime.now().toString())
            it.addHeader(Headers.SENDER, name)
            it.body = body
        }
        try {
            queue.put(msg)
        } catch (e: InterruptedException) {
            log.error(e) { "Fail to send message." }
        }
    }

    fun stop() {
        isStopped = true
        try {
            queue.put(Message.POISON_PILL)
        } catch (e: InterruptedException) {
            log.error(e) { "Exception caught. " }
        }
    }
}