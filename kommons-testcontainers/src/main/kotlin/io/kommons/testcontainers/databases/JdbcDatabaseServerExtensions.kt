package io.kommons.testcontainers.databases

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.testcontainers.containers.JdbcDatabaseContainer

/**
 * [JdbcDatabaseServer] 속성 정보를 Map으로 빌드합니다.
 */
fun JdbcDatabaseServer.buildJdbcProperties(): Map<String, Any?> {
    return mapOf(
        "driver-class-name" to getDriverClassName(),
        "jdbc-url" to getJdbcUrl(),
        "username" to getUsername(),
        "password" to getPassword(),
        "database" to getDatabaseName()
    )
}

/**
 * Database Container에 접속하기 위한 [HikariDataSource] 를 제공합니다.
 *
 * @param T  JdbcDatabaseContainer type
 * @return [HikariDataSource] instance
 */
fun <T: JdbcDatabaseContainer<T>> JdbcDatabaseContainer<T>.getDataSource(): HikariDataSource {
    val config = HikariConfig().also {
        it.driverClassName = driverClassName
        it.jdbcUrl = jdbcUrl
        it.username = username
        it.password = password
    }
    return HikariDataSource(config)
}