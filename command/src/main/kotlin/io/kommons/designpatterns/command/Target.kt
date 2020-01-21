package io.kommons.designpatterns.command

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * Base class for spell targets
 */
abstract class Target {

    companion object: KLogging()

    var size: Size? = null
    var visibility: Visibility? = null

    abstract override fun toString(): String

    fun printStatus() {
        log.info { "$this [size=$size] [visibility=$visibility]" }
    }
}