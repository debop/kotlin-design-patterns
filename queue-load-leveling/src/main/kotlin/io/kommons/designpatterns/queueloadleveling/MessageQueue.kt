package io.kommons.designpatterns.queueloadleveling

import io.kommons.logging.KLogging
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

class MessageQueue {

    companion object: KLogging()

    private val queue: BlockingQueue<Message> = ArrayBlockingQueue(1024)

    fun submitMsg(msg: Message) {
        queue.add(msg)
    }

    fun retrieveMsg(): Message? = queue.poll()
}