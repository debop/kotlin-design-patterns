package io.kommons.designpatterns.lazy.loading

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test

class KotlinHolderTest {

    private val holder = KotlinHolder()

    @Test
    fun `lazy loading`() = runBlocking<Unit> {
        withTimeout(3000) {
            holder.getHeavy().shouldNotBeNull()
            holder.getHeavy().shouldNotBeNull()
            holder.getHeavy().shouldNotBeNull()
            holder.getHeavy().shouldNotBeNull()
            holder.getHeavy().shouldNotBeNull()
        }
    }

}