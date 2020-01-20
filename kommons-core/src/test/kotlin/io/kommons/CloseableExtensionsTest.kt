package io.kommons

import io.kommons.logging.KLogging
import io.mockk.clearMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Duration
import java.util.concurrent.TimeoutException

class CloseableExtensionsTest {

    companion object: KLogging()

    val closeable = mockk<AutoCloseable>(relaxUnitFun = true)

    @BeforeEach
    fun beforeEach() {
        clearMocks(closeable)
    }

    @Test
    fun `close 시에 예외가 발생해도 끝내기`() {
        every { closeable.close() } throws RuntimeException("Boom!")

        closeable.closeSafe()

        verify(exactly = 1) { closeable.close() }
        confirmVerified(closeable)
    }

    @Test
    fun `close 시 timeout 적용`() {

        every { closeable.close() } answers { Thread.sleep(1000) }

        var captured: Throwable? = null
        closeable.closeTimeout(Duration.ofMillis(10)) { e -> captured = e }

        captured.shouldNotBeNull()
        captured shouldBeInstanceOf TimeoutException::class

        verify(exactly = 1) { closeable.close() }
        confirmVerified(closeable)
    }

    @Test
    fun `use in AutoCloseable`() {
        var captured = false

        closeable.use {
            captured = true
        }

        captured.shouldBeTrue()

        verify(exactly = 1) { closeable.close() }
        confirmVerified(closeable)
    }
}