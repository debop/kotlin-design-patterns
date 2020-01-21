package io.kommons.designpatterns.throttling

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

/**
 * CallsCount
 *
 * @author debop
 */
class CallsCount {

    companion object: KLogging()

    private val tenantCallsCount = ConcurrentHashMap<String, AtomicLong>()

    fun addTenant(tenantName: String) {
        tenantCallsCount.putIfAbsent(tenantName, AtomicLong(0))
    }

    fun incrementCount(tenantName: String) {
        tenantCallsCount[tenantName]?.incrementAndGet()
    }

    fun getCount(tenantName: String): Long =
        tenantCallsCount[tenantName]?.get() ?: 0L

    fun reset() {
        log.debug { "Resetting the map." }
        tenantCallsCount.forEach { (tenant, _) ->
            tenantCallsCount[tenant]?.set(0)
        }
    }
}