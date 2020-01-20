package io.kommons.designpatterns.businessdelegate

import io.kommons.designpatterns.businessdelegate.services.EjbService
import io.kommons.designpatterns.businessdelegate.services.JmsService
import io.mockk.clearAllMocks
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BusinessDelegateTest {

    private lateinit var ejbService: EjbService
    private lateinit var jmsService: JmsService
    private lateinit var businessLookup: BusinessLookup
    private lateinit var businessDelegate: BusinessDelegate

    @BeforeEach
    fun setup() {
        ejbService = spyk(EjbService())
        jmsService = spyk(JmsService())

        businessLookup = spyk(BusinessLookup())
        businessLookup.setEjbService(ejbService)
        businessLookup.setJmsService(jmsService)

        businessDelegate = spyk(BusinessDelegate(businessLookup))
    }

    @Test
    fun `verify business delegate`() {

        val client = Client(businessDelegate)

        // set service type to EJB
        businessDelegate.setServiceType(ServiceType.EJB)

        client.doTask()

        verify(exactly = 1) { businessDelegate.doTask() }
        verify(exactly = 1) { ejbService.doProcessing() }
        verify(exactly = 0) { jmsService.doProcessing() }

        clearAllMocks()

        // set service type to JMS
        businessDelegate.setServiceType(ServiceType.JMS)

        client.doTask()

        verify(exactly = 1) { businessDelegate.doTask() }
        verify(exactly = 0) { ejbService.doProcessing() }
        verify(exactly = 1) { jmsService.doProcessing() }
    }
}