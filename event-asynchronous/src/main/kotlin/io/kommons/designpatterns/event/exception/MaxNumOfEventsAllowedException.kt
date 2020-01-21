package io.kommons.designpatterns.event.exception

/**
 * Type of Exception raised when the max number of allowed events is exceeded
 *
 * @author debop
 */
class MaxNumOfEventsAllowedException: RuntimeException {

    constructor(): super()
    constructor(message: String): super(message)
    constructor(message: String, cause: Throwable?): super(message, cause)
    constructor(cause: Throwable?): super(cause)

}