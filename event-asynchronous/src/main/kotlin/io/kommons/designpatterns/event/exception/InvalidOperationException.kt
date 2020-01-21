package io.kommons.designpatterns.event.exception

/**
 * InvalidOperationException
 *
 * @author debop
 */
class InvalidOperationException: RuntimeException {

    constructor(): super()
    constructor(message: String): super(message)
    constructor(message: String, cause: Throwable?): super(message, cause)
    constructor(cause: Throwable?): super(cause)

}