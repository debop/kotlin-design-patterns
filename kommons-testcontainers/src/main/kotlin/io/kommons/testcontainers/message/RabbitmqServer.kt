package io.kommons.testcontainers.message

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * Docker를 이용하여 Rabbitmq Server를 실행합니다.
 *
 * ```kotlin
 * val rabbitmqServer = RabbitmqServer().apply { start() }
 * ```
 *
 * @author debop
 */
class RabbitmqServer @JvmOverloads constructor(
    tag: String = DEFAULT_TAG,
    useDefaultPort: Boolean = false
): GenericContainer<RabbitmqServer>("$IMAGE_NAME:$tag"), GenericServer {

    companion object: KLogging() {
        const val IMAGE_NAME = "rabbitmq"
        const val DEFAULT_TAG = "3.7.8"

        const val RABBITMQ_NAME = "rabbitmq"
        const val RABBITMQ_PORT = 5672
    }

    init {
        withExposedPorts(RABBITMQ_PORT)
        withLogConsumer(Slf4jLogConsumer(log))
        setWaitStrategy(Wait.forListeningPort())

        if (useDefaultPort) {
            exposeCustomPorts(RABBITMQ_PORT)
        }
    }

    override fun start() {
        super.start()
        writeToSystemProperties(RABBITMQ_NAME)
    }
}