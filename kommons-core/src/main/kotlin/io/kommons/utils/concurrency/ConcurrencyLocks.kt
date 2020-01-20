package io.kommons.utils.concurrency

import java.util.concurrent.CountDownLatch
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * [CountDownLatch]를 이용하여 `operation`을 수행하고, 대기합니다.
 *
 * ```kotlin
 * val result = 1.withLatch {
 *    // do something
 *    countDown()
 *    42
 * }
 * result shouldEqual 42
 * ```
 *
 * @receiver Int CountDownLatch의 count
 * @param operation [@kotlin.ExtensionFunctionType] Function1<CountDownLatch, T>
 * @return T
 */
inline fun <T> Int.withLatch(operation: CountDownLatch.() -> T): T {
    val latch = CountDownLatch(this)
    val result: T = latch.operation()
    latch.await()
    return result
}


/**
 * [ReentrantReadWriteLock]의 read lock을 걸고 `block`을 실행한다
 *
 * ```kotlin
 * val rwLock = ReenterantReadWriteLock()
 * rwLock.withReadLock {
 *     // do work
 * }
 * ```
 *
 * @receiver ReentrantReadWriteLock
 * @param block Function0<T>
 * @return T
 */
inline fun <T> ReentrantReadWriteLock.withReadLock(block: () -> T): T {
    val readLock = readLock()
    readLock.lock()
    try {
        return block()
    } finally {
        readLock.unlock()
    }
}

/**
 * [ReentrantReadWriteLock]의 write lock을 걸고 `block`을 실행한다.
 *
 * ```kotlin
 * val rwLock = ReenterantReadWriteLock()
 * rwLock.withWriteLock {
 *     // do work
 * }
 * ```
 *
 * @receiver ReentrantReadWriteLock
 * @param block Function0<T>
 * @return T
 */
inline fun <T> ReentrantReadWriteLock.withWriteLock(block: () -> T): T {

    // read lock이 lock 들을 unlock 한다
    val readLock = readLock()
    val readCount = if (writeHoldCount == 0) readHoldCount else 0
    repeat(readCount) { readLock.unlock() }

    val writeLock = writeLock()
    writeLock.lock()
    try {
        return block()
    } finally {
        // 기존 readLock을 다시 lock 으로 복구한다
        repeat(readCount) { readLock.lock() }
        writeLock.unlock()
    }
}