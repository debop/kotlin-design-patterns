package io.kommons.designpatterns.facade

import io.kommons.logging.KLogging
import io.kommons.logging.info

class DwarvenGoldDigger: DwarvenMineWorker {

    companion object: KLogging()

    override val name: String = "Dwarf gold digger"


    override fun work() {
        log.info { "$name digs for gold." }
    }
}