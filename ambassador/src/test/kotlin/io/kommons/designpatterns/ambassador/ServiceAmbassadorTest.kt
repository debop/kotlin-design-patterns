package io.kommons.designpatterns.ambassador

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

/**
 * ServiceAmbassadorTest
 *
 * @author debop
 */
class ServiceAmbassadorTest {

    @Test
    fun `run service ambassador`() {
        val result = ServiceAmbassador().doRemoteFunction(10)
        assertTrue { result == RemoteServiceInterface.FAILURE || result == 100L }
    }
}