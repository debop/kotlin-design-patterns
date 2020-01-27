package io.kommons.designpatterns.poisonpill

import io.kommons.designpatterns.poisonpill.Message.Headers
import io.kommons.logging.KLogging
import io.kommons.logging.error
import io.kommons.logging.info

class Consumer(private val name: String, private val queue: MqSubscribePoint) {

    companion object: KLogging()

    fun consume() {
        while (true) {
            try {
                val msg = queue.take()
                if (msg == Message.POISON_PILL) {
                    log.info { "Consumer $name receive request to terminate." }
                    break
                }
                val sender = msg.getHeader(Headers.SENDER)
                val body = msg.body
                log.info { "Message [$body] from [$sender] received by [$name]" }
            } catch (e: InterruptedException) {
                log.error(e) { "Exception caught." }
                return
            }
        }
    }
}