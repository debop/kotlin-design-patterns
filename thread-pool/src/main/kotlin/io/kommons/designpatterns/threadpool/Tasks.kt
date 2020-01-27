package io.kommons.designpatterns.threadpool

import io.kommons.logging.KLogging
import java.util.concurrent.atomic.AtomicInteger

abstract class Task(val timeMs: Int) {

    companion object: KLogging() {
        private val ID_GENERATOR = AtomicInteger()
    }

    val id = ID_GENERATOR.incrementAndGet()

    override fun toString(): String = "${javaClass.simpleName} id=$id timeMs=$timeMs"
}

class CoffeeMakingTask(numCups: Int): Task(numCups * TIME_PER_CUP) {

    companion object: KLogging() {
        private const val TIME_PER_CUP = 100
    }
}

class PotatoPeelingTask(numPotato: Int): Task(numPotato * TIME_PER_POTATO) {

    companion object: KLogging() {
        private const val TIME_PER_POTATO = 200
    }

}