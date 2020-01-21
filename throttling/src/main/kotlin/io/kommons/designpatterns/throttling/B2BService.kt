package io.kommons.designpatterns.throttling

import io.kommons.designpatterns.throttling.timer.Throttler
import io.kommons.logging.KLogging
import io.kommons.logging.debug
import io.kommons.logging.error
import java.util.concurrent.ThreadLocalRandom

/**
 * A service which accepts a tenant and throttles the resource based on the time given to the tenant.
 *
 * @author debop
 */
class B2BService(private val throttler: Throttler,
                 private val callsCount: CallsCount) {

    private companion object: KLogging()

    init {
        throttler.start()
    }

    fun dummyCustomerApi(tenant: Tenant): Int {
        val tenantName = tenant.name
        val count = callsCount.getCount(tenantName)
        log.debug { "Counter for ${tenant.name}: $count" }

        if (count >= tenant.allowedCallsPerSecond) {
            log.error { "API access per second limit reached for: $tenantName" }
            return -1
        }

        callsCount.incrementCount(tenantName)
        return getRandomCustomerId()
    }

    private fun getRandomCustomerId(): Int =
        ThreadLocalRandom.current().nextInt(1, 10_000)
}