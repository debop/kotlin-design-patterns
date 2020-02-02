package io.kommons.designpatterns.servicelocator

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * The service cache implementation which will cache services that are being created. On first hit,
 * the cache will be empty and thus any service that is being requested, will be created fresh and
 * then placed into the cache map. On next hit, if same service name will be requested, it will be
 * returned from the cache
 */
class ServiceCache {

    companion object: KLogging()

    private val cache = mutableMapOf<String, Service>()

    fun getService(serviceName: String): Service? {
        return cache[serviceName]?.also {
            val name = it.name
            val id = it.id
            log.info { "(cache call) Fetched service $name($id) from cache...!" }
        }
    }

    fun addService(newService: Service) {
        cache[newService.name] = newService
    }
}