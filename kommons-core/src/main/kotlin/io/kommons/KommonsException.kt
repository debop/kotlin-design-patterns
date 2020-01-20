package io.kommons

/**
 * KommonsException
 *
 * @author debop
 */
open class KommonsException: RuntimeException {
    constructor(): super()
    constructor(msg: String): super(msg)
    constructor(msg: String, cause: Throwable?): super(msg, cause)
    constructor(cause: Throwable?): super(cause)
}