package io.kommons

import io.kommons.junit.jupiter.random.RandomizedTest
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeGreaterThan
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture

@RandomizedTest
class LazyValueTest {

    @Test
    fun `값 계산은 지연되어야 한다`() {
        val x = LazyValue { System.currentTimeMillis() }
        val createdTime = System.currentTimeMillis()

        x.isInitialized.shouldBeFalse()
        Thread.sleep(100)
        x.value shouldBeGreaterThan createdTime
        x.isInitialized.shouldBeTrue()
    }


    @RepeatedTest(5)
    fun `Asynchronous 지연 계산을 수행`() {

        val x = LazyValue { System.nanoTime() }

        val startTime = System.nanoTime()

        val lazyValue = CompletableFuture.supplyAsync {
            Thread.sleep(100)
            x.value
        }

        val time = lazyValue.get()
        time shouldBeGreaterThan startTime
    }

    @Test
    fun `map lazy value`() {

        val x1 = LazyValue { 42 }
        val x2 = x1.map { it * 2 }

        x1.isInitialized.shouldBeFalse()
        x2.isInitialized.shouldBeFalse()

        x2.value shouldEqualTo 84

        x1.isInitialized.shouldBeTrue()
        x2.isInitialized.shouldBeTrue()
    }

    @Test
    fun `flatmap lazy value`() {

        val x1 = LazyValue { LazyValue { 42 } }
        val x2 = x1.flatMap { r -> r.map { it * 2 } }

        x1.isInitialized.shouldBeFalse()
        x2.isInitialized.shouldBeFalse()

        x2.value shouldEqualTo 84

        x1.isInitialized.shouldBeTrue()
        x2.isInitialized.shouldBeTrue()
    }
}