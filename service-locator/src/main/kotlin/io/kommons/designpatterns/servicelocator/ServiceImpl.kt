package io.kommons.designpatterns.servicelocator

import io.kommons.logging.KLogging
import io.kommons.logging.info
import kotlin.random.Random

/**
 * This is a single service implementation of a sample service. This is the actual service that will
 * process the request. The reference for this service is to be looked upon in the JNDI server that
 * can be set in the web.xml deployment descriptor
 */
class ServiceImpl(override val name: String): Service {

    companion object: KLogging() {
        val random = Random(System.currentTimeMillis())
    }

    override val id: Int = random.nextInt(1000) + 1

    override fun execute() {
        log.info { "Service $name is not executing with id $id" }
    }
}