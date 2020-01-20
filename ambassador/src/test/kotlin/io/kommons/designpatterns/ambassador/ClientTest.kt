package io.kommons.designpatterns.ambassador

import io.kommons.logging.KLogging
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

/**
 * ClientTest
 *
 * @author debop
 */
class ClientTest {

    companion object: KLogging()

    @Test
    fun `run remote service via ambassador`() {
        val client = Client()
        val result = client.useService(10)

        assertTrue { result == RemoteServiceInterface.FAILURE || result == 100L }
    }
}