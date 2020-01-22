package io.kommons.designpatterns.facade

import io.kommons.logging.KLogging
import io.kommons.logging.info

class DwarvenCartOperator: DwarvenMineWorker {

    companion object: KLogging()

    override val name: String = "Dwarf cart operator"


    override fun work() {
        log.info { "$name moves gold chunks out of the mine." }
    }
}