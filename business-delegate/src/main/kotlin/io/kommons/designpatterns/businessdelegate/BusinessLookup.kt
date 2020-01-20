package io.kommons.designpatterns.businessdelegate

import io.kommons.designpatterns.businessdelegate.ServiceType.EJB
import io.kommons.designpatterns.businessdelegate.ServiceType.JMS
import io.kommons.designpatterns.businessdelegate.services.BusinessService
import io.kommons.designpatterns.businessdelegate.services.EjbService
import io.kommons.designpatterns.businessdelegate.services.JmsService

/**
 * BusinessLookup
 *
 * @author debop
 * @since 27/09/2019
 */
class BusinessLookup {

    private lateinit var ejbService: EjbService
    private lateinit var jmsService: JmsService


    fun setEjbService(ejbService: EjbService) {
        this.ejbService = ejbService
    }

    fun setJmsService(jmsService: JmsService) {
        this.jmsService = jmsService
    }

    fun getBusinessService(serviceType: ServiceType): BusinessService {
        return when (serviceType) {
            EJB -> ejbService
            JMS -> jmsService
        }
    }
}