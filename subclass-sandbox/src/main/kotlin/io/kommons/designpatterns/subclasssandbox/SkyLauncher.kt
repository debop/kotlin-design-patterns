package io.kommons.designpatterns.subclasssandbox

import io.kommons.logging.KLogging

class SkyLauncher: Superpower {

    companion object: KLogging()

    override fun activate() {
        move(0.0, 0.0, 20.0)
        playSound("SKYLAUNCH_SOUND", 1)
        spawnParticles("SKYLAUNCH_PARTICLE", 100)
    }
}