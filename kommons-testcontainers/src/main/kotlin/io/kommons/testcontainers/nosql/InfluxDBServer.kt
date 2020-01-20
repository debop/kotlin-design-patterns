package io.kommons.testcontainers.nosql

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.InfluxDBContainer
import org.testcontainers.containers.output.Slf4jLogConsumer

/**
 * InfluxDBServer
 *
 * NOTE: url 과 InfluxDBContainer#getUrl() 이 JVM Name으로 충돌을 일으켜서, kotlin의 url은 @JvmField를 getter/setter를 만들지 않게 했음
 * @author debop
 */
class InfluxDBServer(tag: String = VERSION,
                     useDefaultPort: Boolean = false,
                     @JvmField override var url: String = ""): InfluxDBContainer<InfluxDBServer>(tag), GenericServer {

    companion object: KLogging() {
        const val INFLUXDB_NAME = "influxdb"
    }

    override val host: String by lazy { this.containerIpAddress }
    override val port: Int by lazy { getMappedPort(INFLUXDB_PORT) }

    /**
     * [InfluxDBContainer.getUrl]와 [GenericServer.url]이 충돌이 나서, @JvmField를 이용하여 설정하였다.
     */
    @Suppress("RedundantOverride")
    override fun getUrl(): String {
        return super.getUrl()
    }

    init {
        withLogConsumer(Slf4jLogConsumer(log))

        if (useDefaultPort) {
            exposeCustomPorts(INFLUXDB_PORT)
        }
    }

    override fun start() {
        super.start()
        writeToSystemProperties(INFLUXDB_NAME)
        url = super.getUrl()
    }
}