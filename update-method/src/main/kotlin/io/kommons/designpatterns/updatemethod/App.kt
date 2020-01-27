package io.kommons.designpatterns.updatemethod

import io.kommons.logging.KotlinLogging
import io.kommons.logging.error

class App

private val log = KotlinLogging.logger { }
private const val GAME_RUNNING_TIME = 5000L

fun main() {
    try {
        val world = World()
        val skeleton1 = Skeleton(1, 10)
        val skeleton2 = Skeleton(2, 70)
        val statue = Statue(3, 20)

        world.addEntity(skeleton1)
        world.addEntity(skeleton2)
        world.addEntity(statue)

        world.run()
        Thread.sleep(GAME_RUNNING_TIME)
        world.stop()
    } catch (e: InterruptedException) {
        log.error(e) { "Fail to run" }
    }
}