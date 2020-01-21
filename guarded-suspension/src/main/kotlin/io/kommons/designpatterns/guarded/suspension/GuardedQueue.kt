package io.kommons.designpatterns.guarded.suspension

import io.kommons.logging.KLogging
import io.kommons.logging.info
import java.util.LinkedList
import java.util.Queue
import java.util.concurrent.CountDownLatch

/**
 * GuardedQueue
 */
class GuardedQueue {

    companion object: KLogging()

    private val queue: Queue<Int> = LinkedList<Int>()

    private var latch = CountDownLatch(1)

    fun get(): Int {
        while (queue.isEmpty()) {
            log.info { "Waiting ..." }
            latch.await()
        }
        log.info { "Getting ..." }
        latch = CountDownLatch(1)
        return queue.peek()
    }

    fun put(item: Int) {
        log.info { "Putting" }
        queue.add(item)
        log.info { "Notifying" }

        latch.countDown()
    }
}