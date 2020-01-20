package io.kommons.utils.codec

import io.kommons.junit.jupiter.random.RandomValue
import io.kommons.junit.jupiter.random.RandomizedTest
import io.kommons.toUtf8ByteArray
import io.kommons.toUtf8String
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

@RandomizedTest
abstract class AbstractStringEncoderTest {

    protected abstract val encoder: StringEncoder

    @Test
    fun `encode null or empty`() {
        encoder.encode(null).shouldBeEmpty()
        encoder.encode(ByteArray(0)).shouldBeEmpty()
    }

    @Test
    fun `decode null or empty`() {
        encoder.decode(null).shouldBeEmpty()
        encoder.decode("").shouldBeEmpty()
        encoder.decode(" \t ").shouldBeEmpty()
    }

    @RepeatedTest(10)
    fun `encode decode string`(@RandomValue expected: String) {

        val encoded = encoder.encode(expected.toUtf8ByteArray())
        val decoded = encoder.decode(encoded)

        decoded.toUtf8String() shouldEqual expected
    }

    @RepeatedTest(10)
    fun `encode random bytes`(@RandomValue bytes: ByteArray) {

        val encoded = encoder.encode(bytes)
        val decoded = encoder.decode(encoded)

        decoded shouldEqual bytes
    }
}