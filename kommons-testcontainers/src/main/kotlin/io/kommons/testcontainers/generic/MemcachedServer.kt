package io.kommons.testcontainers.generic

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * Docker를 이용하여 Memcached Server를 실행합니다.
 *
 * ```kotlin
 * val memcachedServer = MemcachedServer().apply { start() }
 * ```
 *
 * @author debop
 */
class MemcachedServer @JvmOverloads constructor(
    tag: String = DEFAULT_TAG,
    useDefaultPort: Boolean = false
): GenericContainer<MemcachedServer>("$IMAGE_NAME:$tag"), GenericServer {

    companion object: KLogging() {
        const val IMAGE_NAME = "memcached"
        const val DEFAULT_TAG = "1.5.11"

        const val MEMCACHED_NAME = "memcached"
        const val MEMCACHED_PORT = 11211
    }

    override val url: String get() = "$host:$port"

    init {
        withExposedPorts(MEMCACHED_PORT)
        withLogConsumer(Slf4jLogConsumer(log))
        setWaitStrategy(Wait.forListeningPort())

        if (useDefaultPort) {
            exposeCustomPorts(MEMCACHED_PORT)
        }
    }

    override fun start() {
        super.start()
        writeToSystemProperties(MEMCACHED_NAME)
    }
}