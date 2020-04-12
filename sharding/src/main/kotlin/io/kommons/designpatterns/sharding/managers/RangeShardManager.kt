package io.kommons.designpatterns.sharding.managers

import io.kommons.designpatterns.sharding.Data
import io.kommons.designpatterns.sharding.DataType.TYPE1
import io.kommons.designpatterns.sharding.DataType.TYPE2
import io.kommons.designpatterns.sharding.DataType.TYPE3
import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * ShardManager with range strategy. This strategy groups related items together in the same shard,
 * and orders them by shard key.
 */
class RangeShardManager: ShardManager() {

    companion object: KLogging()

    override fun storeData(data: Data): Int {
        val shardId = allocateShard(data)
        val shard = shardMap[shardId]!!
        shard.storeData(data)

        log.info { "$data is stored in Shard $shardId" }
        return shardId
    }

    override fun allocateShard(data: Data): Int = when (data.type) {
        TYPE1 -> 1
        TYPE2 -> 2
        TYPE3 -> 3
    }
}