package io.kommons.testcontainers.databases

import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.PostgreSQLContainer

/**
 * 테스트용 데이터베이스 서버를 실행시킵니다.
 *
 * @author debop
 */
object DatabaseFactory {

    @JvmStatic
    val MySQL: MySQLServer by lazy { newMySQLServer() }

    @JvmStatic
    val MariaDB: MariaDBServer by lazy { newMariaDBServer() }

    @JvmStatic
    val PostgreSQL: PostgreSQLServer by lazy { newPostgreSQLServer() }

    /**
     * MySQL Server를 실행합니다.
     *
     * @param tag MySQL version
     * @param useDefaultPort 기본 port를 사용할 것인지 여부
     * @param configuration
     */
    fun newMySQLServer(tag: String = MySQLContainer.DEFAULT_TAG,
                       useDefaultPort: Boolean = false,
                       configuration: String = "") =
        MySQLServer(tag, useDefaultPort, configuration).apply { start() }

    /**
     * MariaDB Server를 실행합니다.
     *
     * @param tag MariaDB version
     * @param useDefaultPort 기본 port를 사용할 것인지 여부
     * @param configuration
     */
    fun newMariaDBServer(tag: String = MariaDBContainer.DEFAULT_TAG,
                         useDefaultPort: Boolean = false,
                         configuration: String = "") =
        MariaDBServer(tag, useDefaultPort, configuration).apply { start() }

    /**
     * PostgreSQL 서버를 실행합니다.
     *
     * @param tag PostgreSQL version
     * @param useDefaultPort 기본 port를 사용할 것인지 여부
     */
    fun newPostgreSQLServer(tag: String = PostgreSQLContainer.DEFAULT_TAG,
                            useDefaultPort: Boolean = false) =
        PostgreSQLServer(tag, useDefaultPort).apply { start() }
}