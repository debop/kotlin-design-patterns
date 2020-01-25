package io.kommons.designpatterns.semaphore

import io.kommons.logging.KLogging
import org.junit.jupiter.api.Test

class AppTest {

    companion object: KLogging()

    @Test
    fun `customer buy fruit`() {
        val shop = FruitShop()
        val customerNames = listOf("Peter", "Paul", "Mary", "John", "Ringo", "George")

        val customers = customerNames.map { Customer(it, shop) }
        customers.forEach { it.start() }
        customers.map { it.join() }
    }
}