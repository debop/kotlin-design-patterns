package io.kommons.testcontainers.generic

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * ArangoDB Server for Testing
 *
 * see https://hub.docker.com/_/arangodb
 * @author debop
 */
class ArangoDBServer @JvmOverloads constructor(
    tag: String = DEFAULT_TAG,
    useDefaultPort: Boolean = false,
    password: String? = null
): GenericContainer<ArangoDBServer>("$IMAGE_NAME:$tag"), GenericServer {

    companion object: KLogging() {
        const val IMAGE_NAME = "arangodb"
        const val DEFAULT_TAG = "3.5"
        const val ARANGODB_PORT = 8529
    }

    init {
        withExposedPorts(ARANGODB_PORT)
        withLogConsumer(Slf4jLogConsumer(log))
        setWaitStrategy(Wait.forListeningPort())

        password?.let { withEnv("ARANGO_ROOT_PASSWORD", it) } ?: withEnv("ARANGO_NO_AUTH", "1")

        if (useDefaultPort) {
            exposeCustomPorts(ARANGODB_PORT)
        }
    }

    override fun start() {
        super.start()
        writeToSystemProperties(IMAGE_NAME)
    }
}