package io.kommons.designpatterns.threadpool

import io.kommons.logging.KotlinLogging
import io.kommons.logging.info
import java.util.concurrent.Executors

class App

private val log = KotlinLogging.logger { }

fun main() {

    log.info { "Program started" }

    val tasks = listOf(
        PotatoPeelingTask(3),
        PotatoPeelingTask(6),
        CoffeeMakingTask(2),
        CoffeeMakingTask(6),
        PotatoPeelingTask(4),
        CoffeeMakingTask(2),
        PotatoPeelingTask(4),
        CoffeeMakingTask(9),
        PotatoPeelingTask(3),
        CoffeeMakingTask(2),
        PotatoPeelingTask(4),
        CoffeeMakingTask(2),
        CoffeeMakingTask(7),
        PotatoPeelingTask(4),
        PotatoPeelingTask(5)
    )

    val executor = Executors.newFixedThreadPool(3)

    tasks.forEach { task ->
        executor.execute { Worker(task).invoke() }
    }

    executor.shutdown()
    while (!executor.isTerminated) {
        Thread.yield()
    }
    log.info { "Program finished" }

}