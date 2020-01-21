package io.kommons.designpatterns.producer.consumer

import io.kommons.logging.KLogging
import io.kommons.logging.info

class Consumer(val name: String, val queue: ItemQueue) {

    companion object: KLogging()

    @Throws(InterruptedException::class)
    fun consume() {
        val item = queue.take()
        log.info { "Consumer [$name] consume item[${item.id}] produced by[${item.producer}]" }
    }
}