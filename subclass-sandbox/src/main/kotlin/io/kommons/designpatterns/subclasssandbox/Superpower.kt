package io.kommons.designpatterns.subclasssandbox

import io.kommons.logging.KLogging
import io.kommons.logging.info

interface Superpower {

    companion object: KLogging()

    fun activate()

    @JvmDefault
    fun move(x: Double, y: Double, z: Double) {
        log.info { "Move to ($x, $y, $z)" }
    }

    @JvmDefault
    fun playSound(soundName: String, volume: Int) {
        log.info { "Play $soundName with volume $volume" }
    }

    @JvmDefault
    fun spawnParticles(particleType: String, count: Int) {
        log.info { "Spawn $count particles with type $particleType" }
    }
}