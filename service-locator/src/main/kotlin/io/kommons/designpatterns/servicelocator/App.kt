package io.kommons.designpatterns.servicelocator

import io.kommons.logging.KotlinLogging

/**
 * The Service Locator pattern is a design pattern used in software development to encapsulate the
 * processes involved in obtaining a service with a strong abstraction layer. This pattern uses a
 * central registry known as the "service locator", which on request returns the information
 * necessary to perform a certain task.
 *
 * <p>In this example we use the Service locator pattern to lookup JNDI-services and cache them for
 * subsequent requests.
 * <br>
 *
 * @author saifasif
 */
class App

private val log = KotlinLogging.logger { }

fun main() {
    ServiceLocator.getService("jndi/serviceA")!!.execute()
    ServiceLocator.getService("jndi/serviceB")!!.execute()

    ServiceLocator.getService("jndi/serviceA")!!.execute()
    ServiceLocator.getService("jndi/serviceA")!!.execute()
}