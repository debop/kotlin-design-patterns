package io.kommons.designpatterns.retry

import io.github.resilience4j.decorators.Decorators
import io.github.resilience4j.retry.Retry
import io.github.resilience4j.retry.RetryConfig
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FindCustomerTest {

    companion object: KLogging()

    @Test
    fun `no exception`() {
        FindCustomer("123").perform() shouldEqual "123"
    }

    @Test
    fun `on exception`() {
        val op = FindCustomer("123", BusinessException("test"))
        assertThrows<BusinessException> {
            op.perform()
        }
    }

    @Test
    fun `result after exception`() {
        val op = FindCustomer("123",
                              CustomerNotFoundException("not found"),
                              DatabaseNotAvailableException("not available"))

        try {
            op.perform()
        } catch (e: CustomerNotFoundException) {
            // ignore
        }
        try {
            op.perform()
        } catch (e: DatabaseNotAvailableException) {
            // ignore
        }

        op.perform() shouldEqual "123"
    }

    @Test
    fun `retry when exception`() {
        val retry = Retry.of("retyr") {
            RetryConfig.custom<String>()
                .maxAttempts(3)
                .retryExceptions(CustomerNotFoundException::class.java)
                .intervalFunction { 100L }
                .build()
        }.apply {
            eventPublisher.onRetry {
                log.warn("Error. retry=${it.numberOfRetryAttempts}")
            }
        }

        val op = FindCustomer("123", CustomerNotFoundException("not found"))

        // Use Decorators
        val decorated = Decorators
            .ofSupplier { op.perform() }
            .withRetry(retry)
            .decorate()

        val customerId = decorated.get()
        customerId shouldEqual "123"
    }
}