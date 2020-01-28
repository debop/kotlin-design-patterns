package io.kommons.designpatterns.doublebuffer

import io.kommons.logging.KotlinLogging
import io.kommons.logging.info

class App

private val log = KotlinLogging.logger { }

fun main() {
    val scene = Scene()

    val drawPixels = mapOf(1 to 1, 5 to 6, 3 to 2)
    scene.draw(drawPixels)
    val buffer1 = scene.getBuffer()
    printBlackPixelCoordinate(buffer1)

    val drawPixels2 = mapOf(3 to 7, 6 to 1)
    scene.draw(drawPixels2)
    val buffer2 = scene.getBuffer()
    printBlackPixelCoordinate(buffer2)
}

private fun printBlackPixelCoordinate(buffer: Buffer) {
    val blackPixels = buildString {
        val pixels = buffer.getPixels()
        pixels.forEachIndexed { i, pixel ->
            if (pixel == Pixel.BLACK) {
                val y = i / FrameBuffer.WIDTH
                val x = i % FrameBuffer.WIDTH
                append(" ($x, $y)")
            }
        }
    }
    log.info { "Black Pixels: $blackPixels" }
}