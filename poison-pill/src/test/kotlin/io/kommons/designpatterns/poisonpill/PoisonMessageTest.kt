package io.kommons.designpatterns.poisonpill

import io.kommons.designpatterns.poisonpill.Message.Companion.POISON_PILL
import io.kommons.designpatterns.poisonpill.Message.Headers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PoisonMessageTest {

    @Test
    fun `add header to POISON_PILL`() {
        assertThrows<UnsupportedOperationException> {
            POISON_PILL.addHeader(Headers.SENDER, "sender")
        }
    }

    @Test
    fun `get header from POISON_PILL`() {
        assertThrows<UnsupportedOperationException> {
            POISON_PILL.getHeader(Headers.SENDER)
        }
    }

    @Test
    fun `get headers from POISON_PILL`() {
        assertThrows<UnsupportedOperationException> {
            POISON_PILL.getHeaders()
        }
    }

    @Test
    fun `set body to POISON_PILL`() {
        assertThrows<UnsupportedOperationException> {
            POISON_PILL.body = "test message."
        }
    }

    @Test
    fun `get body from POISON_PILL`() {
        assertThrows<UnsupportedOperationException> {
            val body = POISON_PILL.body
        }
    }
}