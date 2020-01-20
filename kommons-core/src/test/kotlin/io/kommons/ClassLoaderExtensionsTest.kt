package io.kommons

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class ClassLoaderExtensionsTest {

    companion object: KLogging()

    @Test
    fun `load ClassLoader by current context`() {
        val currentClassLoader = getContextClassLoader()
        val systemClassLoader = getSystemClassLoader()

        currentClassLoader shouldEqual systemClassLoader
    }

    @Test
    fun `get class loader from class type`() {
        getClassLoader(KLogging::class) shouldEqual getContextClassLoader()
        getClassLoader(ValueObject::class) shouldEqual getClassLoader<ValueObject>()
    }
}