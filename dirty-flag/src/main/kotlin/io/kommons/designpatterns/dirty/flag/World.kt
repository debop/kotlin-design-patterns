package io.kommons.designpatterns.dirty.flag

class World {
    private val countries: List<String> = ArrayList()
    private val df = DataFetcher()

    fun fetch(): List<String> {
        val data = df.fetch()
        return if (data.isEmpty()) countries else data
    }
}