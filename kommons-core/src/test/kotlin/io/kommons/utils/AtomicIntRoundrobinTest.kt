package io.kommons.utils

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.streams.toList

class AtomicIntRoundrobinTest {

    companion object: KLogging()

    @Test
    fun `병렬에서 Atomic 한가`() {
        val size = 10000
        val atomic = AtomicIntRoundrobin(size)

        val ids = List(size) { it }.parallelStream().map { atomic.next() }.toList()
        ids.size shouldEqualTo size
        ids.toSet().size shouldEqualTo size
    }

    @Test
    fun `increment round robin`() {
        val atomic = AtomicIntRoundrobin(4)

        atomic.get() shouldEqualTo 0

        atomic.next() shouldEqualTo 1
        atomic.next() shouldEqualTo 2
        atomic.next() shouldEqualTo 3
        atomic.next() shouldEqualTo 0
        atomic.next() shouldEqualTo 1
        atomic.next() shouldEqualTo 2
        atomic.next() shouldEqualTo 3
        atomic.next() shouldEqualTo 0
    }

    @Test
    fun `set new value`() {

        val atomic = AtomicIntRoundrobin(16)

        atomic.next() shouldEqualTo 1
        atomic.next() shouldEqualTo 2

        atomic.set(1)

        atomic.get() shouldEqualTo 1
        atomic.next() shouldEqualTo 2

        assertThrows<AssertionError> {
            atomic.set(Int.MAX_VALUE)
        }
    }
}