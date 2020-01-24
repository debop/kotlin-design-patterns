package io.kommons.designpatterns.producer.consumer

import io.kommons.logging.KLogging
import io.kommons.logging.info
import org.apache.commons.lang3.RandomUtils

class Producer(private val name: String, private val queue: ItemQueue) {

    companion object: KLogging()

    private var itemId = 0

    @Throws(InterruptedException::class)
    fun produce() {
        val item = Item(itemId++, name)
        queue.put(item)

        log.info { "Producer [$name] produce item[${item.id}]" }
        Thread.sleep(RandomUtils.nextLong(100, 200))
    }
}