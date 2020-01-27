package io.kommons.designpatterns.updatemethod

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldContainSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WorldTest {

    private lateinit var world: World

    @BeforeEach
    fun setup() {
        world = World()
    }

    @Test
    fun `run world`() {
        world.run()
        world.isRunning.shouldBeTrue()
    }

    @Test
    fun `stop world`() {
        world.stop()
        world.isRunning.shouldBeFalse()
    }

    @Test
    fun `add entity`() {
        val entity = Skeleton(1)
        world.addEntity(entity)
        world.entities shouldContainSame listOf(entity)
    }
}