package io.kommons.designpatterns.poisonpill

import java.util.concurrent.ArrayBlockingQueue

interface MqPublishPoint {
    fun put(msg: Message)
}

interface MqSubscribePoint {
    fun take(): Message
}

interface MessageQueue: MqPublishPoint, MqSubscribePoint


class SimpleMessageQueue(bound: Int): MessageQueue {

    private val queue = ArrayBlockingQueue<Message>(bound)

    override fun put(msg: Message) {
        queue.put(msg)
    }

    override fun take(): Message {
        return queue.take()
    }

}