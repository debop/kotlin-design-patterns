package io.kommons.designpatterns.businessdelegate.services

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * EjbService
 *
 * @author debop
 * @since 27/09/2019
 */

class EjbService: BusinessService {

    companion object: KLogging()

    override fun doProcessing() {
        log.info { "EjbService is not processing ..." }
    }

}