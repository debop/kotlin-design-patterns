package io.kommons.designpatterns.semaphore

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Semaphore is an implementation of a semaphore lock.
 */
class Semaphore(val licenses: Int): Lock {

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    private var counter = licenses

    val availableLicenses: Int get() = counter

    override fun acquire() {
        lock.withLock {
            while (counter == 0) {
                condition.await()
            }
            counter--
        }
    }

    override fun release() {
        lock.withLock {
            if (counter < licenses) {
                counter++
                condition.signal()
            }
        }
    }
}