package io.kommons.testcontainers.databases

import io.kommons.testcontainers.GenericServer

/**
 * Jdbc Database Server의 기본 속성을 표현하는 Interface
 *
 * @author debop
 */
interface JdbcDatabaseServer: GenericServer {

    fun getDriverClassName(): String

    fun getJdbcUrl(): String

    fun getUsername(): String?

    fun getPassword(): String?

    fun getDatabaseName(): String?

}