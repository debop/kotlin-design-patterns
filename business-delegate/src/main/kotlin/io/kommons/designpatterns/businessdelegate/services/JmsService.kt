package io.kommons.designpatterns.businessdelegate.services

import io.kommons.logging.KLogging
import io.kommons.logging.info

class JmsService: BusinessService {

    companion object: KLogging()

    override fun doProcessing() {
        log.info { "JmsService is processing ..." }
    }
}