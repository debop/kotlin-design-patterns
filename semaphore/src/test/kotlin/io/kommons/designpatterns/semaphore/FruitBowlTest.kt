package io.kommons.designpatterns.semaphore

import io.kommons.designpatterns.semaphore.Fruit.FruitType
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test

class FruitBowlTest {

    companion object: KLogging()

    @Test
    fun `fruit bowl`() {
        val bowl = FruitBowl()
        bowl.countFruit shouldEqualTo 0

        repeat(10) {
            bowl.put(Fruit(FruitType.LEMON))
            bowl.countFruit shouldEqualTo it + 1
        }

        for (i in 9 downTo 0) {
            bowl.take().shouldNotBeNull()
            bowl.countFruit shouldEqualTo i
        }

        bowl.take().shouldBeNull()
    }
}