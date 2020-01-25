package io.kommons.designpatterns.datalocality.game

import io.kommons.logging.KLogging
import io.kommons.logging.info

class AiComponentManager(private val numEntities: Int) {

    companion object: KLogging() {
        const val MAX_ENTITIES = 10_000
    }

    private val components: Array<AiComponent?> = Array(MAX_ENTITIES) { null }


    fun start() {
        log.info { "Start AI Game Component" }
        repeat(numEntities) { components[it] = AiComponent() }
    }

    fun update() {
        log.info { "Update AI Game Component" }

        components
            .asSequence()
            .takeWhile { it != null }
            .forEach { it!!.update() }
    }
}

class PhysicsCompoentManager(private val numEntities: Int) {

    companion object: KLogging() {
        const val MAX_ENTITIES = 10_000
    }

    private val components: Array<PhysicsComponent?> = Array(MAX_ENTITIES) { null }

    fun start() {
        log.info { "Start Physics Game Component" }
        repeat(numEntities) { components[it] = PhysicsComponent() }
    }

    fun update() {
        log.info { "Update Physics Game Component" }

        components
            .asSequence()
            .takeWhile { it != null }
            .forEach { it!!.update() }
    }
}

class RenderCompoentManager(private val numEntities: Int) {

    companion object: KLogging() {
        const val MAX_ENTITIES = 10_000
    }

    private val components: Array<RenderComponent?> = Array(MAX_ENTITIES) { null }

    fun start() {
        log.info { "Start Render Game Component" }
        repeat(numEntities) { components[it] = RenderComponent() }
    }

    fun render() {
        log.info { "Update Render Game Component" }

        components
            .asSequence()
            .takeWhile { it != null }
            .forEach { it!!.render() }
    }
}