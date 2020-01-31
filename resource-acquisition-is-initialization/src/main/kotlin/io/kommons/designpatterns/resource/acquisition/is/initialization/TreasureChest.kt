package io.kommons.designpatterns.resource.acquisition.`is`.initialization

import io.kommons.logging.KLogging
import io.kommons.logging.info
import java.io.Closeable

class TreasureChest: Closeable {

    companion object: KLogging()

    init {
        log.info { "Treasure chest opens." }
    }

    override fun close() {
        log.info { "Treasure chest closes." }
    }
}