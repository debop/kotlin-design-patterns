package io.kommons.utils

import io.kommons.LazyValue
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.Date


class KotlinDetectorTest {

    @Test
    fun `detect kotlin environment`() {
        KotlinDetector.isKotlinPresent().shouldBeTrue()
    }

    @Test
    fun `detect kotlin class`() {
        Date::class.java.isKotlinType().shouldBeFalse()
        Instant::class.java.isKotlinType().shouldBeFalse()


        Sequence::class.java.isKotlinType().shouldBeTrue()
        LazyValue::class.java.isKotlinType().shouldBeTrue()
    }

}