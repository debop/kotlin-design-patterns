package io.kommons.testcontainers.databases

import io.kommons.logging.KLogging
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * Docker를 이용하여 MySQL Server 를 실행합니다.
 *
 * ```kotlin
 * val mysqlServer = MySQLServer().apply { start() }
 * ```
 *
 * @author debop
 */
class MySQLServer @JvmOverloads constructor(
    tag: String = DEFAULT_TAG,
    useDefaultPort: Boolean = false,
    configuration: String = "",
    username: String = "root",
    password: String = ""
): MySQLContainer<MySQLServer>("$IMAGE:$tag"), JdbcDatabaseServer {

    companion object: KLogging() {
        const val MYSQL_8_TAG = "8.0.17"
        const val DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver"
    }

    override fun getDriverClassName(): String = DRIVER_CLASS_NAME
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
            exposeCustomPorts(MYSQL_PORT)
        }
    }


    override fun start() {
        super.start()

        writeToSystemProperties(NAME, buildJdbcProperties())
    }
}