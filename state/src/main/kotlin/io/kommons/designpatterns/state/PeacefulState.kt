package io.kommons.designpatterns.state

import io.kommons.logging.KLogging
import io.kommons.logging.debug

/**
 * PeacefulState
 *
 * @author debop
 */
class PeacefulState(val mammoth: Mammoth): State {

    companion object: KLogging()

    override fun observe() {
        log.debug { "$mammoth is calm and peaceful." }
    }

    override fun onEtnerState() {
        log.debug { "$mammoth calms down." }
    }
}