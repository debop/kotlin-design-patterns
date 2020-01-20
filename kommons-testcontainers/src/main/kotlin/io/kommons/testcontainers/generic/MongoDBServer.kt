package io.kommons.testcontainers.generic

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * Docker로 실행되는 MongoDB Server
 *
 * ```kotlin
 * val mongodbServer = MongoDBServer().apply { start() }
 * ```
 *
 * @author debop
 */
class MongoDBServer @JvmOverloads constructor(
    tag: String = DEFAULT_TAG,
    useDefaultPort: Boolean = false
): GenericContainer<MongoDBServer>("$IMAGE_NAME:$tag"), GenericServer {

    companion object: KLogging() {
        const val IMAGE_NAME = "mongo"
        const val DEFAULT_TAG = "4.2"

        const val MONGODB_NAME = "mongodb"
        const val MONGODB_PORT = 27017
    }

    override val url: String get() = "$MONGODB_NAME://$host:$port"

    init {
        withExposedPorts(MONGODB_PORT)
        withLogConsumer(Slf4jLogConsumer(log))
        setWaitStrategy(Wait.forListeningPort())



        if (useDefaultPort) {
            exposeCustomPorts(MONGODB_PORT)
        }
    }

    override fun start() {
        super.start()
        writeToSystemProperties(MONGODB_NAME)
    }
}