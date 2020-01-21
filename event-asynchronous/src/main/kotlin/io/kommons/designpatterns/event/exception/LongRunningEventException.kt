package io.kommons.designpatterns.event.exception

/**
 * Type of Exception raised when the Operation being invoked is Long Running
 *
 * @author debop
 */
class LongRunningEventException: RuntimeException {

    constructor(): super()
    constructor(message: String): super(message)
    constructor(message: String, cause: Throwable?): super(message, cause)
    constructor(cause: Throwable?): super(cause)

}