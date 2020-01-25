package io.kommons.designpatterns.datalocality

import io.kommons.designpatterns.datalocality.game.GameEntity
import io.kommons.logging.KotlinLogging
import io.kommons.logging.info

class App

private val log = KotlinLogging.logger { }
private const val NUM_ENTITIES = 5

fun main() {
    log.info { "Start Game Application using Data-Locality pattern" }

    val gameEntity = GameEntity(NUM_ENTITIES)

    gameEntity.start()
    gameEntity.update()
}