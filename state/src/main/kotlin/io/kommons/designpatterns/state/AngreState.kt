package io.kommons.designpatterns.state

import io.kommons.logging.KLogging
import io.kommons.logging.debug

/**
 * Angre state
 */
class AngreState(private val mammoth: Mammoth): State {

    companion object: KLogging()

    override fun observe() {
        log.debug { "$mammoth is furious!" }
    }

    override fun onEtnerState() {
        log.debug { "$mammoth gets angry!" }
    }
}