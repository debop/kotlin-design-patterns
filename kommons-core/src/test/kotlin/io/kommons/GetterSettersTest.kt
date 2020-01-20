package io.kommons

import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class GetterSettersTest {

    @Test
    fun `getter operation`() {
        val map = hashMapOf<Int, String>()

        val getter = getterOperation<Int, String?> { key -> map[key] }
        val setter = setterOperation { key: Int, value: String -> map[key] = value }

        getter[1].shouldBeNull()

        setter[1] = "abc"
        getter[1] shouldEqual "abc"

        setter[2] = "가나다"
        getter[2] shouldEqual "가나다"
    }
}