package io.kommons.testcontainers.nosql

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * Elassandra Server
 *
 * Elassandra[https://www.elassandra.io/] = Elasticsearch + Apache Cassandra
 *
 * @author debop
 */
class ElassandraServer @JvmOverloads constructor(
    val tag: String = ELASSANDRA_TAG,
    val useDefaultPort: Boolean = false
): GenericContainer<ElassandraServer>("$ELASSANDRA_IMAGE:$ELASSANDRA_TAG"), GenericServer {

    companion object: KLogging() {
        const val ELASSANDRA_NAME = "elassandra"
        const val ELASSANDRA_IMAGE = "strapdata/$ELASSANDRA_NAME"
        const val ELASSANDRA_TAG = "6.8.4.0"

        const val INTRANODE_PORT = 7000
        const val INTRANODE_TLS_PORT = 7001
        const val JMX_PORT = 7199
        const val ELASTICSEARCH_PORT = 9200
        const val ELASTICSEARCH_TCP_PORT = 9300
        const val CQL_PORT = 9042
        const val ENCRYPTED_CQL_PORT = 9142

    }

//    override val host: String get() = containerIpAddress
    override val port: Int get() = getMappedPort(CQL_PORT)

    val elasticsearchPort: Int get() = getMappedPort(ELASTICSEARCH_PORT)
    val elasticsearclTcpPort: Int get() = getMappedPort(ELASTICSEARCH_TCP_PORT)
    val cqlPort: Int get() = getMappedPort(CQL_PORT)
    val encryptedCqlPort: Int get() = getMappedPort(ENCRYPTED_CQL_PORT)


    init {

        withLogConsumer(Slf4jLogConsumer(CassandraServer.log))
        setWaitStrategy(Wait.forListeningPort())

        if (useDefaultPort) {
            exposeCustomPorts(INTRANODE_PORT,
                              INTRANODE_TLS_PORT,
                              JMX_PORT,
                              ELASTICSEARCH_PORT,
                              ELASTICSEARCH_TCP_PORT,
                              CQL_PORT,
                              ENCRYPTED_CQL_PORT)
        }
    }

    override fun start() {
        super.start()
        val extraMap = hashMapOf<String, Any?>(
            "elasticsearch.port" to elasticsearchPort,
            "elasticsearch.tcp.port" to elasticsearclTcpPort,
            "cql.port" to cqlPort,
            "encrypted.cql.port" to encryptedCqlPort
        )
        writeToSystemProperties(ELASSANDRA_NAME, extraMap)
    }
}