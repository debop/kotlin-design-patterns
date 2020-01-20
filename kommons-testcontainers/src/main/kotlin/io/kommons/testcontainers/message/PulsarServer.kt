package io.kommons.testcontainers.message

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.PulsarContainer

/**
 * Dokcer를 이용하여 Pulsar 서버를 실행합니다.
 *
 * ```kotlin
 * val pulsarServer = PulsarServer().apply { start() }
 * ```
 *
 *
 * @author debop
 */
class PulsarServer @JvmOverloads constructor(tag: String = DEFAULT_TAG,
                                             useDefaultPort: Boolean = false): PulsarContainer(tag), GenericServer {

    companion object: KLogging() {
        const val DEFAULT_TAG = "2.4.0"
        const val PULSAR_NAME = "pulsar"
    }

    override val url: String get() = pulsarBrokerUrl

    init {
        if (useDefaultPort) {
            exposeCustomPorts(BROKER_PORT, BROKER_HTTP_PORT)
        }
    }

    override fun start() {
        super.start()

        val props = mapOf(
            "pulsarBrokerUrl" to pulsarBrokerUrl,
            "brokerPort" to getMappedPort(BROKER_PORT),
            "brokerHttpPort" to getMappedPort(BROKER_HTTP_PORT)
        )

        writeToSystemProperties(PULSAR_NAME, props)
    }
}