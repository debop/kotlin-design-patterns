package io.kommons.testcontainers.aws

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.dynamodb.DynaliteContainer

/**
 * Dynalite as a stand-in for DynamoDB
 *
 * ```
 * val dynamoDBServer = DynaliteServer().apply { start() }
 * ```
 *
 * @author debop
 */
class DynaliteServer(
    val tag: String = DEFAULT_TAG,
    val useDefaultPort: Boolean = false): DynaliteContainer("$IMAGE_NAME:$tag"), GenericServer {

    companion object: KLogging() {
        const val IMAGE_NAME = "quay.io/testcontainers/dynalite"
        const val DEFAULT_TAG = "v1.2.1-1"
        const val DYNALITE_NAME = "dynalite"
        const val DYNALITE_PORT = 4567
    }

    init {
        withExposedPorts(DYNALITE_PORT)
        withLogConsumer(Slf4jLogConsumer(log))
        setWaitStrategy(Wait.forListeningPort())

        if (useDefaultPort) {
            exposeCustomPorts(DYNALITE_PORT)
        }
    }

    override fun start() {
        super.start()
        writeToSystemProperties(DYNALITE_NAME)
    }
}