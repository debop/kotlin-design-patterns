package io.kommons.designpatterns.doublebuffer

import io.kommons.logging.KLogging
import java.util.Arrays

interface Buffer {

    fun clear(x: Int, y: Int)

    fun draw(x: Int, y: Int)

    fun clearAll()

    fun getPixels(): Array<Pixel>
}

class FrameBuffer: Buffer {

    companion object: KLogging() {
        const val WIDTH = 10
        const val HEIGHT = 8
    }

    private val pixels = Array(WIDTH * HEIGHT) { Pixel.WHITE }

    override fun clear(x: Int, y: Int) {
        pixels[getIndex(x, y)] = Pixel.WHITE
    }

    override fun draw(x: Int, y: Int) {
        pixels[getIndex(x, y)] = Pixel.BLACK
    }

    override fun clearAll() {
        pixels.indices.forEach {
            pixels[it] = Pixel.WHITE
        }
        Arrays.fill(pixels, Pixel.WHITE)
    }

    override fun getPixels(): Array<Pixel> {
        return pixels
    }

    private fun getIndex(x: Int, y: Int): Int = x + WIDTH * y

}