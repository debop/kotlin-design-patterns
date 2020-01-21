package io.kommons.designpatterns.producer.consumer

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

open class ItemQueue {

    val queue: BlockingQueue<Item> = LinkedBlockingQueue(5)

    @Throws(InterruptedException::class)
    fun put(item: Item) {
        queue.put(item)
    }

    @Throws(InterruptedException::class)
    fun take(): Item {
        return queue.take()
    }
}