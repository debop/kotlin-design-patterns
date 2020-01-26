package io.kommons.designpatterns.sharding

import io.kommons.designpatterns.sharding.managers.ShardManager
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

abstract class AbstractShardManagerTest {

    companion object: KLogging()

    abstract val shardManager: ShardManager

    @BeforeEach
    fun setup() {
        shardManager.clearShard()
    }

    @Test
    fun `add new shard`() {
        val shard = Shard(1)
        shardManager.addNewShard(shard).shouldBeTrue()

        val map = getShardMap(shardManager)

        map.size shouldEqualTo 1
        map[shard.id] shouldEqual shard
    }

    @Test
    fun `remove shard by id`() {
        val shard = Shard(1)
        shardManager.addNewShard(shard).shouldBeTrue()
        shardManager.removeShardById(shard.id).shouldBeTrue()

        val map = getShardMap(shardManager)
        map.shouldBeEmpty()
    }

    @Test
    fun `get shard by id`() {
        val shard = Shard(1)
        shardManager.addNewShard(shard).shouldBeTrue()

        shardManager.getShardById(shard.id) shouldEqual shard
    }

    @RepeatedTest(10)
    open fun `store data by hash`() {
        val shard1 = Shard(1)
        val shard2 = Shard(2)
        val shard3 = Shard(3)
        shardManager.addNewShard(shard1)
        shardManager.addNewShard(shard2)
        shardManager.addNewShard(shard3)

        val data = Data(1, "test", DataType.TYPE1)
        shardManager.storeData(data)
        shardManager.getShardById(shard1.id)?.getDataById(data.key) shouldEqual data
    }

    @Suppress("UNCHECKED_CAST")
    protected fun getShardMap(shardManager: ShardManager): Map<Int, Shard> {
        val field = ShardManager::class.java.getDeclaredField("shardMap")
        field.isAccessible = true
        return field.get(shardManager) as Map<Int, Shard>
    }
}