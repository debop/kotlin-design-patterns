package io.kommons.utils.concurrency

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * 이름을 가지는 Thread를 생성해주는 Factory입니다.
 *
 * @author debop
 */
class NamedThreadFactory @JvmOverloads constructor(val prefix: String? = DEFAULT_PREFIX,
                                                   val isDaemon: Boolean = false): ThreadFactory {

    companion object: KLogging() {
        const val DEFAULT_PREFIX = "thread"
    }

    val name: String
        get() = if (prefix.isNullOrBlank()) DEFAULT_PREFIX else prefix

    val group: ThreadGroup by lazy { ThreadGroup(Thread.currentThread().threadGroup, name) }

    private val threadNumber = AtomicInteger(1)

    inline fun newThread(crossinline body: () -> Unit): Thread {
        return newThread(Runnable { body() })
    }

    override fun newThread(runnable: Runnable): Thread {
        val threadName = name + "-" + threadNumber.andIncrement
        val thread = Thread(group, runnable, threadName).also {
            it.isDaemon = isDaemon
            it.priority = Thread.NORM_PRIORITY
        }
        log.debug { "Create new thread. thread name=$threadName" }
        return thread
    }
}