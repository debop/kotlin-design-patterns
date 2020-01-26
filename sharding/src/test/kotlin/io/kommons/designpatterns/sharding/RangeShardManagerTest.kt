package io.kommons.designpatterns.sharding

import io.kommons.designpatterns.sharding.managers.RangeShardManager
import io.kommons.designpatterns.sharding.managers.ShardManager
import io.kommons.logging.KLogging

class RangeShardManagerTest: AbstractShardManagerTest() {

    companion object: KLogging()

    override val shardManager: ShardManager = RangeShardManager()

}