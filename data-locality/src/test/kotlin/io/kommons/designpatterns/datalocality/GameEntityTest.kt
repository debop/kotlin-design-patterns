package io.kommons.designpatterns.datalocality

import io.kommons.designpatterns.datalocality.game.GameEntity
import io.kommons.logging.KLogging
import io.kommons.logging.info
import org.junit.jupiter.api.Test

class GameEntityTest {

    companion object: KLogging() {
        private const val NUM_ENTITIES = 5
    }

    @Test
    fun `run game entities`() {
        log.info { "Start Game Application using Data-Locality pattern" }

        val gameEntity = GameEntity(NUM_ENTITIES)

        gameEntity.start()
        gameEntity.update()
    }
}