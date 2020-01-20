package io.kommons.designpatterns.ambassador

import io.kommons.designpatterns.ambassador.util.randomProviderOf
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test

/**
 * RemoveServiceTest
 *
 * @author debop
 */
class RemoveServiceTest {

    companion object: KLogging()

    @Test
    fun `failed call`() {
        val service = RemoteService(randomProviderOf { 0.21 })
        val result = service.doRemoteFunction(10)
        result shouldEqualTo RemoteServiceInterface.FAILURE
    }

    @Test
    fun `success call`() {
        val service = RemoteService(randomProviderOf { 0.2 })
        val result = service.doRemoteFunction(10)
        result shouldEqualTo 100
    }

}