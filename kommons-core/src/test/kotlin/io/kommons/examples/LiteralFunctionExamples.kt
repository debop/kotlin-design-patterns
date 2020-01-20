package io.kommons.examples

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test

class LiteralFunctionExamples {

    companion object: KLogging()

    @Test
    fun `generate sequence`() {
        val sequence = generateSequence(0) { it + 1 }
        val list: Sequence<Int> = sequence.takeWhile { it < 100 }

        list.count() shouldEqualTo 100
    }

    @Test
    fun `build sequence`() {
        val sequence: Sequence<Int> = sequence {
            var i = 0
            while (true) {
                yield(i)
                i += 1
            }
        }
        val list: Sequence<Int> = sequence.takeWhile { it < 100 }
        list.count() shouldEqualTo 100
    }


    @Test
    fun `fibonacci sequence builder`() {
        val seq: Sequence<Int> = sequence {
            var a = 0
            var b = 1

            yield(1)
            log.debug { "Generate item=1" }

            while (true) {
                yield(a + b)
                val tmp = a + b
                log.debug { "Generate item=$tmp" }
                a = b
                b = tmp
            }
        }

        val list = seq.take(10).toList()

        list shouldEqual listOf(1, 1, 2, 3, 5, 8, 13, 21, 34, 55)
    }

    @Test
    fun `fibonacci sequence test`() {
        val seq: Sequence<Int> = sequence {
            var terms = Pair(0, 1)

            while (true) {
                yield(terms.first)
                terms = Pair(terms.second, terms.first + terms.second)
            }
        }

        val list = seq.take(10).toList()

        list shouldEqual listOf(0, 1, 1, 2, 3, 5, 8, 13, 21, 34)
    }
}