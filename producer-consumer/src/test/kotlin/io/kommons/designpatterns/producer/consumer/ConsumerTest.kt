package io.kommons.designpatterns.producer.consumer

import io.kommons.logging.KLogging
import io.mockk.clearMocks
import io.mockk.confirmVerified
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ConsumerTest {

    companion object: KLogging() {
        private const val ITEM_COUNT = 5
    }

    private val itemQueue = spyk(ItemQueue())

    @BeforeEach
    fun setup() {
        clearMocks(itemQueue)
    }

    @Test
    fun `consume items`() {

        repeat(ITEM_COUNT) {
            itemQueue.put(Item(it, "producer"))
        }

        val consumer = Consumer("consumer", itemQueue)

        repeat(ITEM_COUNT) {
            consumer.consume()
        }

        verify(exactly = ITEM_COUNT) { itemQueue.put(any()) }
        verify(exactly = ITEM_COUNT) { itemQueue.take() }
        confirmVerified(itemQueue)
    }
}