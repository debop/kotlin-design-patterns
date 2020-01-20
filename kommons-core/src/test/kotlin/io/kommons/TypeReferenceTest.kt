package io.kommons

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TypeReferenceTest {

    @Test
    fun `assignable from`() {
        Any::class.isAssignableFrom(Any::class).shouldBeTrue()

        Number::class.java.isAssignableFrom(Long::class.java).shouldBeFalse()

        Number::class.isAssignableFrom(Long::class).shouldBeFalse()
        Long::class.isAssignableFrom(Number::class).shouldBeFalse()

        Long::class.isAssignableFrom(BigDecimal::class).shouldBeFalse()
        BigDecimal::class.isAssignableFrom(Long::class).shouldBeFalse()
    }
}