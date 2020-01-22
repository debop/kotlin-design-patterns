package io.kommons.designpatterns.facade

import io.kommons.logging.KLogging
import io.kommons.logging.info

class DwarvenTunnelDigger: DwarvenMineWorker {

    companion object: KLogging()

    override val name: String = "Dwarven tunnel digger"

    override fun work() {
        log.info { "$name creates another promising tunnel." }
    }
}