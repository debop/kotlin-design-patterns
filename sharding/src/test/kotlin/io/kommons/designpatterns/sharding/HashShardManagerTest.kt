package io.kommons.designpatterns.sharding

import io.kommons.designpatterns.sharding.managers.HashShardManager
import io.kommons.designpatterns.sharding.managers.ShardManager
import io.kommons.logging.KLogging

class HashShardManagerTest: AbstractShardManagerTest() {

    companion object: KLogging()

    override val shardManager: ShardManager = HashShardManager()


}