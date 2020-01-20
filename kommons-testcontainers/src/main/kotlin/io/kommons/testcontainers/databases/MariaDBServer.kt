package io.kommons.testcontainers.databases

import io.kommons.logging.KLogging
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * Docker를 이용하여 MariaDB Server를 실행합니다.
 *
 * ```kotlin
 * val mariadbServer = MariaDBServer().apply { start() }
 * ```
 *
 * @author debop
 */
class MariaDBServer @JvmOverloads constructor(
    tag: String = DEFAULT_TAG,
    useDefaultPort: Boolean = false,
    configuration: String = "",
    username: String = "root",
    password: String = ""
): MariaDBContainer<MariaDBServer>("$IMAGE:$tag"), JdbcDatabaseServer {

    companion object: KLogging() {
        const val MARIADB_PORT = 3306
    }

    override val url: String get() = jdbcUrl

    init {
        if (configuration.isNotBlank()) {
            withConfigurationOverride(configuration)
        }
        withUsername(username)
        withPassword(password)

        setWaitStrategy(Wait.forListeningPort())
        withLogConsumer(Slf4jLogConsumer(log))

        if (useDefaultPort) {
            exposeCustomPorts(MARIADB_PORT)
        }
    }


    override fun start() {
        super.start()

        writeToSystemProperties(NAME, buildJdbcProperties())
    }
}