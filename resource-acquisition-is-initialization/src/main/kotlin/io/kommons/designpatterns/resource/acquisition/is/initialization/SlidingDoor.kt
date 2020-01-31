package io.kommons.designpatterns.resource.acquisition.`is`.initialization

import io.kommons.logging.KLogging
import io.kommons.logging.info
import java.io.Closeable

class SlidingDoor: Closeable {

    companion object: KLogging()

    init {
        log.info { "Sliding door opens." }
    }

    override fun close() {
        log.info { "Sliding door closes." }
    }
}