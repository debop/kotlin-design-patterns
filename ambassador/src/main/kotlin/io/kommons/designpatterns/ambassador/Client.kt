package io.kommons.designpatterns.ambassador

import io.kommons.logging.KLogging
import io.kommons.logging.info

class Client {

    companion object: KLogging()

    private val serviceAmbassador = ServiceAmbassador()

    fun useService(value: Int): Long {
        val result = serviceAmbassador.doRemoteFunction(value)
        log.info { "Service result: $result" }
        return result
    }
}