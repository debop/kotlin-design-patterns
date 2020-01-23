package io.kommons.designpatterns.subclasssandbox

import io.kommons.logging.KLogging
import io.kommons.logging.info
import org.junit.jupiter.api.Test

class SuperpowerTest {

    companion object: KLogging()

    @Test
    fun `superpower example`() {
        log.info { "Use superpower: sky launch" }
        val skyLauncher = SkyLauncher()
        skyLauncher.activate()

        log.info { "Use superpower: ground dive" }
        val groundDive = GroundDive()
        groundDive.activate()
    }
}