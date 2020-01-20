package io.kommons.logging

import io.kommons.logging.internal.KLoggerFactory
import org.slf4j.Logger
import kotlin.reflect.KClass

/**
 * Kotlin Logging using Slf4j
 *
 * ```
 * private val log = KotlinLogging.logger {}
 * private val log = KotlinLogging.logger("name")
 * private val log = KotlinLogging.logger(Foo::class)
 * ```
 *
 * @author debop
 */
object KotlinLogging {

    fun logger(action: () -> Unit): Logger = KLoggerFactory.logger(action)

    fun logger(name: String): Logger = KLoggerFactory.logger(name)

    fun logger(clazz: Class<*>): Logger = logger(clazz.name)

    fun logger(kclass: KClass<*>): Logger = logger(kclass.qualifiedName!!)
}