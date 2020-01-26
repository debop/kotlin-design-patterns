package io.kommons.designpatterns.sharding.managers

import io.kommons.designpatterns.sharding.Data
import io.kommons.designpatterns.sharding.Shard
import io.kommons.logging.KLogging

abstract class ShardManager {

    companion object: KLogging()

    protected val shardMap = LinkedHashMap<Int, Shard>()

    /**
     * Add a provided shard instance to shardMap.
     *
     * @param shard new shard instance.
     * @return {@code true} if succeed to add the new instance.
     * {@code false} if the shardId is already existed.
     */
    fun addNewShard(shard: Shard): Boolean {
        val shardId = shard.id
        return if (!shardMap.containsKey(shardId)) {
            shardMap[shardId] = shard
            true
        } else {
            false
        }
    }

    /**
     * Remove a shard instance by provided Id.
     *
     * @param shardId Id of shard instance to remove.
     * @return {@code true} if removed. {@code false} if the shardId is not existed.
     */
    fun removeShardById(shardId: Int): Boolean {
        return if (shardMap.containsKey(shardId)) {
            shardMap.remove(shardId) != null
        } else {
            false
        }
    }

    fun getShardById(shardId: Int): Shard? = shardMap[shardId]

    fun clearShard() {
        shardMap.clear()
    }

    /**
     * Store data in proper shard instance.
     *
     * @param data new data
     * @return id of shard that the data is stored in
     */
    abstract fun storeData(data: Data): Int

    /**
     * Allocate proper shard to provided data.
     *
     * @param data new data
     * @return id of shard that the data should be stored
     */
    protected abstract fun allocateShard(data: Data): Int
}