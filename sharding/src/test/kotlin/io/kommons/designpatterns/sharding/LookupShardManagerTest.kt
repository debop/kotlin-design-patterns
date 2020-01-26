package io.kommons.designpatterns.sharding

import io.kommons.designpatterns.sharding.managers.LookupShardManager
import io.kommons.designpatterns.sharding.managers.ShardManager
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.RepeatedTest

class LookupShardManagerTest: AbstractShardManagerTest() {

    companion object: KLogging()

    override val shardManager: ShardManager = LookupShardManager()

    @RepeatedTest(10)
    override fun `store data by hash`() {
        val shard1 = Shard(1)
        val shard2 = Shard(2)
        val shard3 = Shard(3)
        shardManager.addNewShard(shard1)
        shardManager.addNewShard(shard2)
        shardManager.addNewShard(shard3)

        val data = Data(1, "test", DataType.TYPE1)
        shardManager.storeData(data)

        val lookupMap = getLookupMap(shardManager)
        val shardId = lookupMap[data.key]!!
        shardManager.getShardById(shardId)?.getDataById(data.key) shouldEqual data
    }

    @Suppress("UNCHECKED_CAST")
    protected fun getLookupMap(shardManager: ShardManager): Map<Int, Int> {
        val field = LookupShardManager::class.java.getDeclaredField("lookupMap")
        field.isAccessible = true
        return field.get(shardManager) as Map<Int, Int>
    }
}