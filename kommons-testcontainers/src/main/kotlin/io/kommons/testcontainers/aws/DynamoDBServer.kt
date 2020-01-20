package io.kommons.testcontainers.aws

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * AWS DynamoDB를 로컬에서 테스트 할 수 있는 Mock Server
 *
 * ```kotlin
 * val dynamoDBServer = DynamoDBServer().apply { start() }
 * ```
 *
 * @author debop
 */
class DynamoDBServer @JvmOverloads constructor(
    tag: String = DEFAULT_TAG,
    useDefaultPort: Boolean = false,
    val tableName: String = DEFAULT_TABLE_NAME
): GenericContainer<DynamoDBServer>("$IMAGE_NAME:$tag"), GenericServer {

    companion object: KLogging() {
        const val IMAGE_NAME = "amazon/dynamodb-local"
        const val DEFAULT_TAG = "1.11.477"

        const val DYNAMODB_NAME = "dynamodb"
        const val DYNAMODB_PORT = 8000
        const val DEFAULT_TABLE_NAME = "test"
    }

    init {
        withExposedPorts(DYNAMODB_PORT)
        withLogConsumer(Slf4jLogConsumer(log))
        setWaitStrategy(Wait.forListeningPort())

        if (useDefaultPort) {
            exposeCustomPorts(DYNAMODB_PORT)
        }
    }

    override fun start() {
        super.start()
        writeToSystemProperties(DYNAMODB_NAME)
    }
}