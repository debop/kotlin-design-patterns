package io.kommons.designpatterns.dirty.flag

import org.junit.Assert
import org.junit.jupiter.api.Test

class DirtyFlagTest {

    @Test
    fun `Is dirty`() {
        val df = DataFetcher()
        val countries = df.fetch()
        Assert.assertFalse(countries.isEmpty())
    }

    @Test
    fun `Is not dirty`() {
        val df = DataFetcher()
        df.fetch()
        val countries = df.fetch()
        Assert.assertTrue(countries.isEmpty())
    }
}