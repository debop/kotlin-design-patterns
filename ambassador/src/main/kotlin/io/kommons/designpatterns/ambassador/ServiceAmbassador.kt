package io.kommons.designpatterns.ambassador

import io.github.resilience4j.retry.Retry
import io.github.resilience4j.retry.RetryConfig
import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * ServiceAmbassador provides an interface for a [Client] to access ([RemoteService]).
 * The interface adds logging, latency testing and usage of the service in a safe way that will not
 * add stress to the remote service when connectivity issues occur.
 *
 * @author debop
 */
class ServiceAmbassador: RemoteServiceInterface {

    companion object: KLogging() {
        private const val RETRIES = 3
        private const val DELAY_MS = 3000L

        private val retry = Retry.of("ambassator") {
            RetryConfig.custom<Double>()
                .maxAttempts(RETRIES)
                .intervalFunction { DELAY_MS }
                .build()
        }.apply {
            eventPublisher.onError {
                log.info { "Failed to reach remote: ${it.numberOfRetryAttempts}" }
            }
        }
    }

    override fun doRemoteFunction(value: Int): Long {
        return safeCall(value)
    }

    private fun safeCall(value: Int): Long {
        return retry.executeCallable {
            val startTime = System.currentTimeMillis()

            val result = RemoteService.INSTANCE.doRemoteFunction(value)

            val timeTaken = System.currentTimeMillis() - startTime
            log.info { "Timee taken (ms): $timeTaken" }
            result
        }
    }
}