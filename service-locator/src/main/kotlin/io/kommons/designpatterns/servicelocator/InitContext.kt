package io.kommons.designpatterns.servicelocator

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * For JNDI lookup of services from the web.xml. Will match name of the service name that is being
 * requested and return a newly created service object with the name
 */
class InitContext {

    companion object: KLogging()

    /**
     * Perform the lookup based on the service name. The returned object will need to be casted into a
     * {@link Service}
     *
     * @param serviceName a string
     * @return an [Any?]
     */
    fun lookup(serviceName: String): Any? = when (serviceName) {
        "jndi/serviceA" -> {
            log.info { "Looking up service A and creating new service for A" }
            ServiceImpl(serviceName)
        }
        "jndi/serviceB" -> {
            log.info { "Looking up service B and creating new service for B" }
            ServiceImpl(serviceName)
        }
        else            -> null
    }
}