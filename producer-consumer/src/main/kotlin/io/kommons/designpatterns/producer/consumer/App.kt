package io.kommons.designpatterns.producer.consumer

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class App

fun main() {
    val queue = ItemQueue()
    val executorService = Executors.newFixedThreadPool(5)

    try {
        repeat(4) {
            val producer = Producer("Producer_$it", queue)
            executorService.submit {
                while (true) {
                    producer.produce()
                }
            }
        }

        repeat(2) {
            val consumer = Consumer("Consumer_$it", queue)
            executorService.submit<Any> {
                while (true) {
                    consumer.consume()
                }
            }
        }
    } finally {
        executorService.shutdown()
        executorService.awaitTermination(10, TimeUnit.SECONDS)
        executorService.shutdownNow()
    }
}
