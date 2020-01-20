package io.kommons.testcontainers.nosql

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.elasticsearch.ElasticsearchContainer

/**
 * ElasticsearchServer
 *
 * @author debop
 */
class ElasticsearchServer @JvmOverloads constructor(
    image: String = DEFAULT_OSS_IMAGE,
    tag: String = DEFAULT_TAG,
    useDefaultPort: Boolean = false
): ElasticsearchContainer("$image:$tag"), GenericServer {

    companion object: KLogging() {
        const val DEFAULT_IMAGE = "docker.elastic.co/elasticsearch/elasticsearch"
        const val DEFAULT_OSS_IMAGE = "docker.elastic.co/elasticsearch/elasticsearch-oss"
        const val DEFAULT_TAG = "7.5.1"
        const val DEFAULT_PORT = 9200
        const val DEFAULT_TCP_PORT = 9300
    }

    override val port: Int get() = getMappedPort(DEFAULT_PORT)
    override val url: String get() = httpHostAddress

    init {
        withLogConsumer(Slf4jLogConsumer(log))

        if (useDefaultPort) {
            exposeCustomPorts(DEFAULT_PORT, DEFAULT_TCP_PORT)
        }
    }

    override fun start() {
        super.start()
        writeToSystemProperties("Elasticsearch")
    }
}