package io.kommons.testcontainers.generic

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * Docker를 이용하여 Redis Server를 실행합니다.
 *
 * ```kotlin
 * val redisServer = RedisServer().apply { start() }
 * ```
 *
 * @author debop
 */
class RedisServer @JvmOverloads constructor(tag: String = DEFAULT_TAG,
                                            useDefaultPort: Boolean = false)
    : GenericContainer<RedisServer>("$IMAGE_NAME:$tag"), GenericServer {

    companion object: KLogging() {
        const val IMAGE_NAME = "redis"
        const val DEFAULT_TAG = "5.0.7"

        const val REDIS_NAME = "redis"
        const val REDIS_PORT = 6379
    }

    override val url: String get() = "$REDIS_NAME://$host:$port"

    init {
        withExposedPorts(REDIS_PORT)
        withLogConsumer(Slf4jLogConsumer(log))
        setWaitStrategy(Wait.forListeningPort())

        if (useDefaultPort) {
            exposeCustomPorts(REDIS_PORT)
        }
    }

    override fun start() {
        super.start()
        writeToSystemProperties(REDIS_NAME)
    }
}