package io.kommons.testcontainers.generic

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * Docker를 이용하여 Zipkin Server를 실행합니다.
 *
 * ```kotlin
 * val zipkinServer = ZipkinServer().apply { start() }
 * ```
 *
 * @author debop
 */
class ZipkinServer @JvmOverloads constructor(
    tag: String = DEFAULT_TAG,
    useDefaultPort: Boolean = false
): GenericContainer<ZipkinServer>("$IMAGE_NAME:$tag"), GenericServer {

    companion object: KLogging() {
        const val IMAGE_NAME = "openzipkin/zipkin"
        const val DEFAULT_TAG = "2.11.12"

        const val ZIPKIN_NAME = "zipkin"
        const val ZIPKIN_PORT = 9411
    }

    init {
        withExposedPorts(ZIPKIN_PORT)
        withLogConsumer(Slf4jLogConsumer(log))
        setWaitStrategy(Wait.forListeningPort())

        if (useDefaultPort) {
            exposeCustomPorts(ZIPKIN_PORT)
        }
    }

    override fun start() {
        super.start()
        writeToSystemProperties(ZIPKIN_NAME)
    }
}