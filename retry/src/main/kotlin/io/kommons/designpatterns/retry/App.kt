package io.kommons.designpatterns.retry

import io.github.resilience4j.retry.Retry
import io.github.resilience4j.retry.RetryConfig
import io.kommons.logging.KotlinLogging
import io.kommons.logging.info
import java.time.Duration
import kotlin.math.pow
import kotlin.random.Random

class App

private val log = KotlinLogging.logger { }
private val random = Random(System.currentTimeMillis())

fun main() {
    noError()
    errorNoRetry()
    errorWithRetry()
    errorWithRetryExponentialBackoff()
}

fun noError() {
    val op = FindCustomer("123")
    op.perform()
    log.info { "Sometimes the operation executes with no errors." }
}

fun errorNoRetry() {
    val op = FindCustomer("123", CustomerNotFoundException("not found"))
    try {
        op.perform()
    } catch (e: CustomerNotFoundException) {
        log.info { "Yet the operation will throw an error every once in a while." }
    }
}

fun errorWithRetry() {
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
    val customerId = retry.executeSupplier { op.perform() }

    log.info {
        "However, retrying the operation while ignoring a recoverable error willl eventually yield the result " +
        "$customerId after a number of attempts ${retry.metrics.numberOfSuccessfulCallsWithRetryAttempt}"
    }
}

fun errorWithRetryExponentialBackoff() {
    val retry = Retry.of("retyr") {
        RetryConfig.custom<String>()
            .maxAttempts(6)
            .waitDuration(Duration.ofSeconds(30))
            .retryExceptions(CustomerNotFoundException::class.java)
            .intervalFunction { x -> (2.0.pow(x) * 1000).toLong() + random.nextLong(1000L) }
            .build()
    }.apply {
        eventPublisher.onRetry {
            log.warn("Error. retry=${it.numberOfRetryAttempts}")
        }
    }
    val op = FindCustomer("123", CustomerNotFoundException("not found"))
    val customerId = retry.executeSupplier { op.perform() }

    log.info {
        "However, retrying the operation while ignoring a recoverable error willl eventually yield the result " +
        "$customerId after a number of attempts ${retry.metrics.numberOfSuccessfulCallsWithRetryAttempt}"
    }
}
