package io.kommons.designpatterns.sharding.managers

import io.kommons.designpatterns.sharding.Data
import io.kommons.logging.KLogging
import io.kommons.logging.info
import kotlin.random.Random

/**
 * ShardManager with lookup strategy. In this strategy the sharding logic implements
 * a map that routes a request for data to the shard that contains that data by using
 * the shard key.
 */
class LookupShardManager: ShardManager() {

    companion object: KLogging() {
        private val random = Random(System.currentTimeMillis())
    }

    private val lookupMap = LinkedHashMap<Int, Int>()

    override fun storeData(data: Data): Int {
        val shardId = allocateShard(data)
        lookupMap[data.key] = shardId
        val shard = shardMap[shardId]!!
        shard.storeData(data)

        log.info { "$data is sotred in Shard $shardId" }
        return shardId
    }

    override fun allocateShard(data: Data): Int {
        return lookupMap[data.key] ?: Random.nextInt(shardMap.size - 1) + 1
    }
}