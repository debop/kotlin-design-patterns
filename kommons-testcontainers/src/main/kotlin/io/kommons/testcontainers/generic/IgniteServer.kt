package io.kommons.testcontainers.generic

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * Docker를 이용하여 Local Machine에 Apache Ignite 서버를 실행합니다.
 *
 * ```kotlin
 * val igniteServer = IgniteServer().apply { start() }
 * ```
 *
 * @author debop
 */
class IgniteServer @JvmOverloads constructor(
    tag: String = DEFAULT_TAG,
    useDefaultPort: Boolean = false
): GenericContainer<IgniteServer>("$IMAGE_NAME:$tag"), GenericServer {

    companion object: KLogging() {
        const val IMAGE_NAME = "apacheignite/ignite"
        const val DEFAULT_TAG = "2.7.6"

        const val IGNITE_NAME = "ignite"
        const val DEFAULT_CONFIG_URI: String =
            "https://raw.githubusercontent.com/apache/ignite/master/examples/config/example-cache.xml"

        @JvmField
        val IGNITE_PORTS = intArrayOf(11211, 47100, 47500, 49112)
    }

    override val url: String get() = "$IGNITE_NAME://$host:$port"

    init {
        withExposedPorts(*IGNITE_PORTS.toTypedArray())
        withEnv("CONFIG_URI", DEFAULT_CONFIG_URI)
        withLogConsumer(Slf4jLogConsumer(log))
        setWaitStrategy(Wait.forListeningPort())

        if (useDefaultPort) {
            exposeCustomPorts(*IGNITE_PORTS)
        }
    }

    override fun start() {
        super.start()
        writeToSystemProperties(IGNITE_NAME)
    }
}