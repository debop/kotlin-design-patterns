package io.kommons.logging.internal

import io.kommons.logging.KLogging
import org.slf4j.Logger

/**
 * KLoggerFactory
 *
 * @author debop
 */
internal object KLoggerFactory {

    fun logger(name: String): Logger =
        org.slf4j.LoggerFactory.getLogger(name)

    fun logger(action: () -> Unit): Logger =
        logger(KLoggerNameResolver.name(action))

    fun logger(clazz: Class<*>): Logger = logger(KLoggerNameResolver.name(clazz))

    fun logger(klogging: KLogging): Logger = logger(KLoggerNameResolver.name(klogging.javaClass))
}