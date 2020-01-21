package io.kommons.designpatterns.chain

import io.kommons.logging.KLogging
import io.kommons.logging.debug

abstract class RequestHandler(private val next: RequestHandler?) {

    companion object: KLogging()

    /**
     * Request handler
     */
    open fun handleRequest(request: Request) {
        if (!request.isHandled) {
            next?.handleRequest(request)
        }
    }

    protected fun printHandling(request: Request) {
        log.debug { "$this handle request `$request`" }
    }

    abstract override fun toString(): String
}