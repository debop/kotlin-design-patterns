package io.kommons.designpatterns.throttling

import io.kommons.designpatterns.throttling.timer.Throttler
import io.kommons.designpatterns.throttling.timer.throttller
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test

/**
 * B2BServiceTest
 *
 * @author debop
 */
class B2BServiceTest {

    companion object: KLogging()

    private val callsCount = CallsCount()

    @Test
    fun `call dummyCustomerApi`() {
        val tenant = Tenant("dummyCustomerApi", 2, callsCount)
        val timer: Throttler = throttller { }
        val service = B2BService(timer, callsCount)

        repeat(6) {
            service.dummyCustomerApi(tenant)
        }

        val counter = callsCount.getCount(tenant.name)
        counter shouldEqualTo tenant.allowedCallsPerSecond
    }
}