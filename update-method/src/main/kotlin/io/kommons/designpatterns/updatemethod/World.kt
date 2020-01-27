package io.kommons.designpatterns.updatemethod

import io.kommons.logging.KLogging
import io.kommons.logging.error
import io.kommons.logging.info
import kotlin.concurrent.thread
import kotlin.random.Random

/**
 * The game world class. Maintain all the objects existed in the game frames.
 */
class World {

    companion object: KLogging() {
        private val random = Random(System.currentTimeMillis())
    }

    val entities = mutableListOf<Entity>()

    @Volatile
    var isRunning: Boolean = false
        private set

    /**
     * Main game loop. This loop will always run until the game is over. For
     * each loop it will process user input, update internal status, and render
     * the next frames. For more detail please refer to the game-loop pattern.
     */
    private fun gameLoop() {
        while (isRunning) {
            processInput()
            update()
            render()
        }
    }


    /**
     * Handle any user input that has happened since the last call. In order to
     * simulate the situation in real-life game, here we add a random time lag.
     * The time lag ranges from 50 ms to 250 ms.
     */
    private fun processInput() {
        try {
            val lag = random.nextInt(200) + 50L
            Thread.sleep(lag)
        } catch (e: InterruptedException) {
            log.error(e) { "Fail to process" }
        }
    }

    /**
     * Update internal status. The update method pattern invoke udpate method for each entity in the game.
     */
    private fun update() {
        entities.forEach { it.update() }
    }

    /**
     * Render the next frame. Here we do nothing since it is not related to the pattern.
     */
    private fun render() {
        // Nothing to do.
    }

    fun run() {
        log.info { "Start game." }
        isRunning = true
        thread(start = true) { gameLoop() }
    }

    fun stop() {
        log.info { "Stop game." }
        isRunning = false
    }

    fun addEntity(entity: Entity) {
        entities.add(entity)
    }
}