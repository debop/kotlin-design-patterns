package io.kommons.designpatterns.datalocality.game

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * The game Entity maintains a big array of pointers . Each spin of the game loop, we need to run
 * the following:
 *
 * <p>Update the AI components.
 *
 * <p>Update the physics components for them.
 *
 * <p>Render them using their render components.
 */
class GameEntity(private val numEntities: Int) {

    companion object: KLogging()

    private val aiComponentManager = AiComponentManager(numEntities)
    private val physicsComponentManager = PhysicsCompoentManager(numEntities)
    private val renderComponentManager = RenderCompoentManager(numEntities)

    init {
        log.info { "Init Game with #Entity: $numEntities" }
    }

    /**
     * start all component.
     */
    fun start() {
        log.info { "Start Game" }
        aiComponentManager.start()
        physicsComponentManager.start()
        renderComponentManager.start()
    }

    /**
     * update all component.
     */
    fun update() {
        log.info { "Update Game Component" }

        // Process AI.
        aiComponentManager.update()

        // Update Physics
        physicsComponentManager.update()

        // Draw to screen
        renderComponentManager.render()
    }
}