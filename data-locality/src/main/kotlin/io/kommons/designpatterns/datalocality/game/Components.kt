package io.kommons.designpatterns.datalocality.game

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * Implement different Game component update and render process.
 */
interface Component {

    fun update()

    fun render()

}

/**
 * Implementation of AI Component of Game.
 */
class AiComponent: Component {
    companion object: KLogging()

    override fun update() {
        log.info { "update AI component" }
    }

    override fun render() {
        // Nothing to do.
    }
}

/**
 * Implementation of Physics Component of Game.
 */
class PhysicsComponent: Component {
    companion object: KLogging()

    override fun update() {
        log.info { "update Physics component" }
    }

    override fun render() {
        // Nothing to do.
    }
}

/**
 * Implementation of Render Component of Game.
 */
class RenderComponent: Component {
    companion object: KLogging()

    override fun update() {
        // Nothing to do
    }

    override fun render() {
        log.info { "Render Component" }
    }
}