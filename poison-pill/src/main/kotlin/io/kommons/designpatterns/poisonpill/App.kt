package io.kommons.designpatterns.poisonpill

import io.kommons.logging.KotlinLogging
import kotlin.concurrent.thread

class App

private val log = KotlinLogging.logger { }

fun main() {
    val queue = SimpleMessageQueue(10000)

    val producer = Producer("PRODUCER_1", queue)
    val consumer = Consumer("CONSUMER_1", queue)

    thread(start = true) { consumer.consume() }

    thread(start = true) {
        producer.send("hand shake")
        producer.send("some very important informantion")
        producer.send("bye!")
        producer.stop()
    }
}