package io.kommons.logging

import io.kommons.logging.internal.KLoggerFactory
import org.slf4j.Logger

/**
 * KLogging
 *
 * @author debop
 */
open class KLogging {

    val log: Logger = KLoggerFactory.logger(this.javaClass)

}