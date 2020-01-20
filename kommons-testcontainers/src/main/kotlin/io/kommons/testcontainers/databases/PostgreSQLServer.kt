package io.kommons.testcontainers.databases

import io.kommons.logging.KLogging
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.output.Slf4jLogConsumer

/**
 * PostgreSQLServer
 *
 * ```kotlin
 * val postgreSql = PostgreSQLServer().apply { start() }
 * ```
 *
 * @author debop
 */
class PostgreSQLServer @JvmOverloads constructor(
    tag: String = DEFAULT_TAG,
    useDefaultPort: Boolean = false,
    username: String = "test",
    password: String = "test"
): PostgreSQLContainer<PostgreSQLServer>("$IMAGE:$tag"), JdbcDatabaseServer {

    companion object: KLogging() {
        const val POSTGRESQL_10_TAG = "10.6"
        const val POSTGRESQL_11_TAG = "11.1"
    }

    override val url: String get() = jdbcUrl

    init {
        withUsername(username)
        withPassword(password)

        withLogConsumer(Slf4jLogConsumer(MariaDBServer.log))

        // NOTE: PostgreSQLContainer에 자체적인 Wait strategy가 있으므로, 설정하면 안됩니다.
        // setWaitStrategy(Wait.forListeningPort())

        if (useDefaultPort) {
            exposeCustomPorts(POSTGRESQL_PORT)
        }
    }


    override fun start() {
        super.start()
        writeToSystemProperties(NAME, buildJdbcProperties())
    }
}