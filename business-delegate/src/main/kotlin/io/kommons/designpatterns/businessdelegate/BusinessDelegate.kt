package io.kommons.designpatterns.businessdelegate

import io.kommons.designpatterns.businessdelegate.services.BusinessService

/**
 * BusinessDelegate
 *
 * @author debop
 * @since 27/09/2019
 */
class BusinessDelegate(val lookup: BusinessLookup) {

    private lateinit var businessService: BusinessService
    private lateinit var serviceType: ServiceType

    fun setServiceType(serviceType: ServiceType) {
        this.serviceType = serviceType
    }

    fun doTask() {
        if (!this::serviceType.isInitialized) {
            error("Please set serviceType")
        }
        this.businessService = lookup.getBusinessService(serviceType)
        businessService.doProcessing()
    }

}