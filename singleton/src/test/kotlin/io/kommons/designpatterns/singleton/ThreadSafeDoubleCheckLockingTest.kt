package io.kommons.designpatterns.singleton

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.reflect.InvocationTargetException
import kotlin.test.assertTrue

class ThreadSafeDoubleCheckLockingTest
    : AbstractSingletonTest<ThreadSafeDoubleCheckLocking>({ ThreadSafeDoubleCheckLocking.getInstance() }) {

    @Test
    fun `creating new instance by reflection`() {
        val instance1 = ThreadSafeDoubleCheckLocking.getInstance()

        assertThrows<InvocationTargetException> {
            val constructor = ThreadSafeDoubleCheckLocking::class.java.getDeclaredConstructor()
            constructor.isAccessible = true
            val instance2 = constructor.newInstance() as ThreadSafeDoubleCheckLocking

            assertTrue { instance1 === instance2 }
        }
    }
}