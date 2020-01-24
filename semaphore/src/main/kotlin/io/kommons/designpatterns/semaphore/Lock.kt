package io.kommons.designpatterns.semaphore

/**
 * Lock is an interface for a lock which can be acquired and released.
 */
interface Lock {

    fun acquire()

    fun release()
}