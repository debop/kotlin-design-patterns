package io.kommons.designpatterns.sharding

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ShardTest {

    companion object: KLogging()

    private val data = Data(1, "test", DataType.TYPE1)
    private val shard = Shard(1)

    @BeforeEach
    fun setup() {
        shard.clearData()
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `store data in shard`() {
        shard.storeData(data)

        val field = Shard::class.java.getDeclaredField("dataStore")
        field.isAccessible = true
        val map = field.get(shard) as MutableMap<Int, Data>

        map.size shouldEqualTo 1
        map[data.key] shouldEqual data
    }

    @Test
    fun `clear data in shard`() {
        shard.storeData(data)
        shard.clearData()
        shard.getDataById(data.key).shouldBeNull()
    }
}