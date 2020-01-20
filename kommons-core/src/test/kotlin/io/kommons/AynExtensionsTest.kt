package io.kommons

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.util.Optional

class AynExtensionsTest {

    companion object: KLogging()

    @Test
    fun `Any 를 Optional로 변환하기`() {
        null.toOptional() shouldEqual Optional.empty()
    }

    @Test
    fun `두 값 비교하기`() {
        val a: String? = null
        val b: Int? = null
        areEquals(a, b).shouldBeTrue()

        areEquals(null, null).shouldBeTrue()
        areEquals(null, "").shouldBeFalse()
        areEquals("", "").shouldBeTrue()
        areEquals("a", "a").shouldBeTrue()
    }

    @Test
    fun `두 값 비교하기 with null safe`() {
        val a: String? = null
        val b: Int? = null
        nullSafeEquals(a, b).shouldBeTrue()

        nullSafeEquals(null, null).shouldBeTrue()
        nullSafeEquals(null, "").shouldBeFalse()
        nullSafeEquals("", "").shouldBeTrue()
        nullSafeEquals("a", "a").shouldBeTrue()

        // Array 도 비교할 수 있다
        nullSafeEquals(emptyByteArray, emptyIntArray).shouldBeFalse()
    }

    @Test
    fun `두 array 비교하기`() {
        arrayEquals(byteArrayOf(1), byteArrayOf(1)).shouldBeTrue()
        arrayEquals(byteArrayOf(1), byteArrayOf(2)).shouldBeFalse()
        arrayEquals(byteArrayOf(1), byteArrayOf(1, 2)).shouldBeFalse()
    }

    @Test
    fun `when not null`() {
        listOf(4, null, 3) whenAllNotNull { fail("호출되면 안됩니다.") }
        listOf(4, 5, 7) whenAllNotNull { it shouldContainSame listOf(4, 5, 7) }
    }
}