package io.kommons.designpatterns.subclasssandbox

import io.kommons.logging.KLogging

class GroundDive: Superpower {

    companion object: KLogging()

    override fun activate() {
        move(0.0, 0.0, -20.0)
        playSound("GROUNDDIVE_SOUND", 5)
        spawnParticles("GROUNDDIVE_PARTICLE", 20)
    }
}