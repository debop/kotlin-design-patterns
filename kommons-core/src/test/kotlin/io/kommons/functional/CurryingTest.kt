package io.kommons.functional

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

/**
 * CurryingTest
 *
 * @author debop
 */
class CurryingTest {

    @Test
    fun `currying two input function`() {
        val func: (Int, Int) -> Int = { a, b -> a + b }

        val curried = func.curried()

        curried(1)(2) shouldEqual 3
    }

    @Test
    fun `currying tree input function`() {
        val func: (Int, Int, Int) -> Int = { a, b, c -> a + b + c }

        val curried = func.curried()

        val curried2 = curried(1)(2)

        curried2(3) shouldEqual 6
        curried2(6) shouldEqual 9
    }
}