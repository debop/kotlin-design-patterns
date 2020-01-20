package io.kommons.testcontainers.nosql

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.CassandraContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * CassandraServer
 *
 * @author debop
 */
class CassandraServer @JvmOverloads constructor(
    val tag: String = DEFAULT_TAG,
    val useDefaultPort: Boolean = false
): CassandraContainer<CassandraServer>("$IMAGE:$tag"), GenericServer {

    companion object: KLogging() {
        const val DEFAULT_TAG = "3.11.2"
    }

    override val host: String get() = containerIpAddress
    override val port: Int get() = getMappedPort(CQL_PORT)

    init {
        withLogConsumer(Slf4jLogConsumer(log))
        setWaitStrategy(Wait.forListeningPort())
        // setWaitStrategy(CassandraQueryWaitStrategy())

        if (useDefaultPort) {
            exposeCustomPorts(CQL_PORT)
        }
    }

    override fun start() {
        super.start()
        writeToSystemProperties(IMAGE)
    }
}