package io.kommons

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ToStringBuilderTest {

    @Test
    fun `create with empty string`() {
        assertThrows<AssertionError> {
            ToStringBuilder("")
        }
    }

    @Test
    fun `create with blank string`() {
        assertThrows<AssertionError> {
            ToStringBuilder(" \t  ")
        }
    }

    @Test
    fun `create simple entity`() {
        val builder = ToStringBuilder("object").apply {
            add("a", 1)
            add("b", "two")
        }
        builder.toString() shouldBeEqualTo "object(a=1,b=two)"
    }

    @Test
    fun `create simple entity with null property`() {
        val builder = ToStringBuilder("object").apply {
            add("a", 1)
            add("b", "two")
            add("c", null)
        }
        builder.toString() shouldBeEqualTo "object(a=1,b=two,c=<null>)"
    }
}