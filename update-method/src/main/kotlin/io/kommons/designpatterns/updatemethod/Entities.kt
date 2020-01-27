package io.kommons.designpatterns.updatemethod

import io.kommons.logging.KLogging
import io.kommons.logging.info

abstract class Entity(val id: Int, var position: Int = 0) {

    companion object: KLogging()

    abstract fun update()
}

class Skeleton(id: Int, position: Int = 0): Entity(id, position) {

    companion object: KLogging() {
        private const val PATROLLING_LEFT_BOUNDING = 0
        private const val PATROLLING_RIGHT_BOUNDING = 100
    }

    protected var patrollingLeft: Boolean = false

    override fun update() {
        if (patrollingLeft) {
            position--
            if (position == PATROLLING_LEFT_BOUNDING) {
                patrollingLeft = false
            }
        } else {
            position++
            if (position == PATROLLING_RIGHT_BOUNDING) {
                patrollingLeft = true
            }
        }
        log.info { "Skeleton $id is on position $position." }
    }
}

class Statue(id: Int, private var delay: Int): Entity(id) {

    companion object: KLogging()

    private var frames: Int = 0

    override fun update() {
        if (++frames == delay) {
            shootLighting()
            frames = 0
        }
    }

    private fun shootLighting() {
        log.info { "Statue $id shoots lightning!" }
    }
}
