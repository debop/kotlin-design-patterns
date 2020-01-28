package io.kommons.designpatterns.doublebuffer

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class FrameBufferTest {

    companion object: KLogging()

    @Test
    fun `clear all pixel`() {
        val buffer = FrameBuffer()
        buffer.clearAll()
        buffer.getPixels().all { it == Pixel.WHITE }.shouldBeTrue()

        buffer.draw(0, 0)
        buffer.getPixels().any { it == Pixel.BLACK }.shouldBeTrue()

        buffer.clearAll()
        buffer.getPixels().all { it == Pixel.WHITE }.shouldBeTrue()
    }

    @Test
    fun `clear some pixel`() {
        val buffer = FrameBuffer()

        buffer.getPixels()[0] shouldEqual Pixel.WHITE

        buffer.draw(0, 0)
        buffer.getPixels()[0] shouldEqual Pixel.BLACK

        buffer.clear(0, 0)
        buffer.getPixels()[0] shouldEqual Pixel.WHITE
    }

    @Test
    fun `draw pixel`() {
        val frameBuffer = FrameBuffer()
        frameBuffer.draw(0, 0)
        frameBuffer.getPixels()[0] shouldEqual Pixel.BLACK

        frameBuffer.draw(1, 1)
        frameBuffer.getPixels()[FrameBuffer.WIDTH + 1] shouldEqual Pixel.BLACK
    }
}