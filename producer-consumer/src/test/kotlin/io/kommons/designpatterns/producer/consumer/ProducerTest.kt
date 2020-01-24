package io.kommons.designpatterns.producer.consumer

import io.kommons.logging.KLogging
import io.mockk.clearMocks
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertTimeout
import java.time.Duration


class ProducerTest {

    companion object: KLogging()

    private val itemQueue = mockk<ItemQueue>(relaxUnitFun = true)

    @BeforeEach
    fun setup() {
        clearMocks(itemQueue)
    }

    @Test
    fun `produce items`() {

        assertTimeout(Duration.ofMillis(6000)) {
            val producer = Producer("producer", itemQueue)

            producer.produce()

            verify(exactly = 1) { itemQueue.put(any()) }
            confirmVerified(itemQueue)
        }
    }
}