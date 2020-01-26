package io.kommons.designpatterns.sharding.managers

import io.kommons.designpatterns.sharding.Data
import io.kommons.logging.KLogging
import io.kommons.logging.info
import kotlin.math.absoluteValue

/**
 * ShardManager with hash strategy. The purpose of this strategy is to reduce the
 * chance of hot-spots in the data. It aims to distribute the data across the shards
 * in a way that achieves a balance between the size of each shard and the average
 * load that each shard will encounter.
 */
class HashShardManager: ShardManager() {

    companion object: KLogging()

    override fun storeData(data: Data): Int {
        val shardId = allocateShard(data)
        val shard = shardMap[shardId]!!
        shard.storeData(data)

        log.info { "$data is stored in Shard $shardId" }
        return shardId
    }

    override fun allocateShard(data: Data): Int {
        val shardCount = shardMap.size
        val hash = data.key.absoluteValue % shardCount

        return if (hash == 0) hash + shardCount else hash
    }
}