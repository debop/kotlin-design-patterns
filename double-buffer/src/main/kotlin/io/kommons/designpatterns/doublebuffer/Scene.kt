package io.kommons.designpatterns.doublebuffer

import io.kommons.logging.KLogging
import io.kommons.logging.info

class Scene {

    companion object: KLogging()

    private val frameBuffers = Array<Buffer>(2) { FrameBuffer() }

    private var current: Int = 0
    private var next: Int = 1

    fun draw(coordinateList: Map<Int, Int>) {
        log.info { "Start drawing next frame" }
        log.info { "Current buffer: $current, Next buffer: $next" }

        frameBuffers[next].clearAll()

        coordinateList.forEach { (x, y) ->
            frameBuffers[next].draw(x, y)
        }

        log.info { "Swap current and next buffer" }
        swap()
        log.info { "Finish swapping" }
        log.info { "Current buffer: $current, Next buffer: $next" }
    }

    fun getBuffer(): Buffer {
        log.info { "Get current buffer: $current" }
        return frameBuffers[current]
    }

    private fun swap() {
        val tmp = current
        current = next
        next = tmp
    }
}