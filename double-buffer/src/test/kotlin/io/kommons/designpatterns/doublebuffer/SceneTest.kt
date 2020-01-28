package io.kommons.designpatterns.doublebuffer

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
import org.junit.jupiter.api.Test

class SceneTest {

    companion object: KLogging()

    @Test
    fun `get buffer`() {
        val scene = Scene()
        val current = scene.getBuffer()

        scene.draw(emptyMap())
        val next = scene.getBuffer()
        next shouldNotEqual current

        scene.draw(emptyMap())
        val current2 = scene.getBuffer()
        current2 shouldEqual current
    }
}