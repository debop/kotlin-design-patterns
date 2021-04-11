package io.kommons.testcontainers.message

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * Dokcer를 이용하여 Kafka 서버를 실행합니다.
 *
 * see : https://hub.docker.com/r/confluentinc/cp-kafka
 *
 * ```kotlin
 * val kotlinServer = KafkaServer().apply { start() }
 * ```
 *
 * @author debop
 */
class KafkaServer @JvmOverloads constructor(tag: String = DEFAULT_TAG,
                                            useDefaultPort: Boolean = false,
                                            useTransaction: Boolean = true): KafkaContainer(tag), GenericServer {
    companion object: KLogging() {
        const val DEFAULT_TAG = "5.3.1"
        const val KAFKA_NAME = "kafka"
    }

//    override val host: String get() = bootstrapServers
    override val port: Int get() = firstMappedPort

    init {
        withEmbeddedZookeeper()
        withLogConsumer(Slf4jLogConsumer(log))
        setWaitStrategy(Wait.forListeningPort())

        // HINT: Transaction 관련 테스트를 위해서는 다음과 같은 값을 넣어줘야 합니다.
        // HINT: 테스트 시에는 transaction log replica 를 1로 설정해야 합니다. (기본은 3)
        // see : https://github.com/testcontainers/testcontainers-java/issues/1816
        if (useTransaction) {
            addEnv("KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR", "1")
            addEnv("KAFKA_TRANSACTION_STATE_LOG_MIN_ISR", "1")
        }

        if (useDefaultPort) {
            exposeCustomPorts(KAFKA_PORT)
        }
    }

    override fun start() {
        super.start()

        val props = mapOf(
            "bootstrapServers" to bootstrapServers,
            "boundPortNumbers" to boundPortNumbers.joinToString()
        )
        writeToSystemProperties(KAFKA_NAME, props)
    }
}