package io.kommons.logging

import org.slf4j.Logger

inline fun Logger.trace(msg: () -> Any?) {
    if (isTraceEnabled) {
        trace(msg()?.toString())
    }
}

inline fun Logger.trace(cause: Throwable?, msg: () -> Any?) {
    if (isTraceEnabled) {
        trace(msg()?.toString(), cause)
    }
}

inline fun Logger.debug(msg: () -> Any?) {
    if (isDebugEnabled) {
        debug(msg()?.toString())
    }
}

inline fun Logger.debug(cause: Throwable?, msg: () -> Any?) {
    if (isDebugEnabled) {
        debug(msg()?.toString(), cause)
    }
}

inline fun Logger.info(msg: () -> Any?) {
    if (isInfoEnabled) {
        info(msg()?.toString())
    }
}

inline fun Logger.info(cause: Throwable?, msg: () -> Any?) {
    if (isInfoEnabled) {
        info(msg()?.toString(), cause)
    }
}

inline fun Logger.warn(msg: () -> Any?) {
    if (isWarnEnabled) {
        warn(msg()?.toString())
    }
}

inline fun Logger.warn(cause: Throwable?, msg: () -> Any?) {
    if (isWarnEnabled) {
        warn(msg()?.toString(), cause)
    }
}

inline fun Logger.error(msg: () -> Any?) {
    if (isErrorEnabled) {
        error(msg()?.toString())
    }
}

inline fun Logger.error(cause: Throwable?, msg: () -> Any?) {
    if (isErrorEnabled) {
        error(msg()?.toString(), cause)
    }
}