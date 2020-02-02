package io.kommons.designpatterns.servicelocator

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeGreaterThan
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test

class ServiceLocatorTest {

    companion object: KLogging()

    @Test
    fun `get non existent service`() {
        ServiceLocator.getService("fantastic/unicorn/service").shouldBeNull()
        ServiceLocator.getService("another/fantastic/unicorn/service").shouldBeNull()
    }

    @Test
    fun `with service cache`() {
        val serviceNames = listOf("jndi/serviceA", "jndi/serviceB")

        serviceNames.forEach { serviceName ->
            val service = ServiceLocator.getService(serviceName)
            service.shouldNotBeNull()
            service.name shouldEqual serviceName
            service.id shouldBeGreaterThan 0
            ServiceLocator.getService(serviceName) shouldEqual service
        }
    }
}