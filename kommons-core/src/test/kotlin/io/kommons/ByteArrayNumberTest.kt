package io.kommons

import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ByteArrayNumberTest {

    @ParameterizedTest
    @ValueSource(ints = [Int.MIN_VALUE, -1000, -1, 0, 1, 100, 1024, 53212, Int.MAX_VALUE])
    fun `convert Int to ByteArray`(number: Int) {
        val bytes = number.toByteArray()
        bytes.size shouldEqualTo 4

        val converted = bytes.toInt()
        converted shouldEqualTo number
    }

    @ParameterizedTest
    @ValueSource(longs = [Long.MIN_VALUE, -1000, -1, 0, 1, 100, 1024, 53212, Long.MAX_VALUE])
    fun `convert Long to ByteArray`(number: Long) {
        val bytes = number.toByteArray()
        bytes.size shouldEqualTo 8

        val converted = bytes.toLong()
        converted shouldEqualTo number
    }
}