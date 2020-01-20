package io.kommons

import java.time.Duration
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@JvmField
val EMPTY_CLOSE_ERROR_HANDLER: (error: Throwable) -> Unit = { }

/**
 * [AutoCloseable]을 안전하게 close 를 수행합니다.
 *
 * @param errorHandler close 시 예외 발생 시 수행할 handler, 기본은 아무 일도 하지 않는다
 */
@JvmOverloads
inline fun AutoCloseable.closeSafe(errorHandler: (error: Throwable) -> Unit = {}) {
    try {
        close()
    } catch (ignored: Throwable) {
        errorHandler(ignored)
    }
}

/**
 * close 시에 [timeout]을 지정하여 수행합니다.
 *
 * @param timeout close 의 최대 수행 시간
 * @param errorHandler close 에서 예외 발생 시 수행할 함수
 */
@JvmOverloads
inline fun AutoCloseable.closeTimeout(timeout: Duration = Duration.ofSeconds(30),
                                      crossinline errorHandler: (error: Throwable) -> Unit = {}) {
    try {
        val future = CompletableFuture.runAsync { closeSafe(errorHandler) }

        val timeoutFuture = CompletableFuture<Unit>()
        val scheduler = Executors.newScheduledThreadPool(1)
        scheduler.schedule({ timeoutFuture.completeExceptionally(TimeoutException()) },
                           timeout.toMillis(),
                           TimeUnit.MILLISECONDS)

        CompletableFuture.anyOf(future, timeoutFuture).join()
    } catch (e: Throwable) {
        errorHandler(e.cause ?: e)
    }
}

inline infix fun <T> AutoCloseable.using(action: (AutoCloseable) -> T): T {
    try {
        return action(this)
    } finally {
        closeSafe()
    }
}