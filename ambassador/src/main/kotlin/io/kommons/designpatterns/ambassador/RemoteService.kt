package io.kommons.designpatterns.ambassador

import io.kommons.designpatterns.ambassador.RemoteServiceInterface.Companion.FAILURE
import io.kommons.designpatterns.ambassador.util.RandomProvider
import io.kommons.designpatterns.ambassador.util.randomProviderOf
import io.kommons.logging.KLogging
import io.kommons.logging.error
import kotlin.math.floor

/**
 * 싱글턴으로 구현된 원격 레거시 어플리케이션으로 표현한 서비스입니다.
 *
 * @author debop
 */
class RemoteService(
    private val randomProvider: RandomProvider = randomProviderOf { Math.random() }
): RemoteServiceInterface {

    companion object: KLogging() {
        const val THRESHOLD = 200
        val INSTANCE: RemoteService by lazy { RemoteService() }
    }

    /**
     * Remote function takes a value and multiplies it by 10 taking a random amount of time. Will
     * sometimes return -1. This imitates connectivity issues a client might have to account for.
     *
     * @param value integer value to be multiplied.
     * @return if waitTime is less than {@link RemoteService#THRESHOLD}, it returns value * 10,
     *     otherwise {@link RemoteServiceInterface#FAILURE}.
     */
    override fun doRemoteFunction(value: Int): Long {

        val waitTime = floor(randomProvider.random() * 1000).toLong()

        try {
            Thread.sleep(waitTime)
        } catch (e: InterruptedException) {
            log.error(e) { "Thread sleep state interrupted." }
        }

        return if (waitTime <= THRESHOLD) value * 10L else FAILURE
    }
}