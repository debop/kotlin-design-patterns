package io.kommons.designpatterns.lazy.loading

import io.kommons.logging.KLogging
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test

/**
 * AbstractHolderTest
 */
abstract class AbstractHolderTest {

    companion object: KLogging()

    /**
     * Get Internal state of the holder value
     */
    abstract fun getInternalHeavyValue(): Heavy?

    /**
     * Request a lazy loading [Heavy] object from the holder
     */
    abstract fun getHeavy(): Heavy?

    @Test
    fun `get heavy instance`() = runBlocking<Unit> {
        withTimeout(3000) {
            getInternalHeavyValue().shouldBeNull()
            getHeavy().shouldNotBeNull()
            getInternalHeavyValue().shouldNotBeNull()

            getInternalHeavyValue() shouldEqual getHeavy()
        }
    }
}