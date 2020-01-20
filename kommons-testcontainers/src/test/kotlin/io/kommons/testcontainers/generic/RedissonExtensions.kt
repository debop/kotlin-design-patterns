package io.kommons.testcontainers.generic

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.codec.SnappyCodecV2
import org.redisson.config.Config

fun redissonConfig(url: String): Config {

    val config = Config()

    config.useSingleServer()
        .setAddress(url)
        .setRetryAttempts(3)
        .setRetryInterval(100)
        .setConnectionMinimumIdleSize(8)

    config.codec = config.codec ?: SnappyCodecV2()
    return config
}

fun redissonClient(url: String): RedissonClient = Redisson.create(redissonConfig(url))