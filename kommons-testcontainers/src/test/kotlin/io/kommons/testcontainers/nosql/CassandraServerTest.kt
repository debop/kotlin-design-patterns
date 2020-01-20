package io.kommons.testcontainers.nosql

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.ResultSet
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.testcontainers.containers.ContainerLaunchException

@Disabled("시간이 많이 걸립니다")
@Execution(ExecutionMode.SAME_THREAD)
class CassandraServerTest {

    companion object: KLogging()

    @Test
    fun `connect to cassandra server`() {
        CassandraServer()
            .apply {
                start()
            }.use { server ->
                val resultSet = performQuery(server, "SELECT release_version FROM system.local")
                resultSet.wasApplied().shouldBeTrue()
                resultSet.one().getString(0).shouldNotBeEmpty()
            }
    }

    @Test
    fun `override configuration`() {
        CassandraServer()
            .apply {
                withConfigurationOverride("cassandra-test-configuration-example")
                start()
            }.use { server ->
                val resultSet = performQuery(server, "SELECT release_version FROM system.local")
                resultSet.wasApplied().shouldBeTrue()
                resultSet.one().getString(0).shouldNotBeEmpty()
            }
    }

    @Test
    fun `start server with empty configuration`() {
        assertThrows<ContainerLaunchException> {
            CassandraServer()
                .apply {
                    withConfigurationOverride("cassandra-empty-configuration")
                    start()
                }
        }
    }

    @Test
    fun `run init script`() {
        CassandraServer()
            .apply {
                withInitScript("initial.cql")
                start()
            }.use { server ->
                testInitScript(server)
            }
    }

    private fun testInitScript(server: CassandraServer) {
        val resultSet = performQuery(server, "SELECT * FROM keySpaceTest.catalog_category")
        resultSet.wasApplied().shouldBeTrue()
        val row = resultSet.one()
        row.getLong(0) shouldEqualTo 1
        row.getString(1) shouldEqual "test_category"
    }

    // NOTE: cassandra driver가 metrics 3.2.+ 를 사용합니다.
    //       metrics 4.+ 사용 시에는 withoutJMXReporting()을 수행하여 기존 JmxReporter 사용을 못하게 한다.
    private fun performQuery(server: CassandraServer, cql: String): ResultSet {
        val cluster = Cluster.builder()
            .withoutJMXReporting()
            .addContactPoint(server.host)
            .withPort(server.port)
            .build()

        //        val reporter = JmxReporter.forRegistry(cluster.metrics.registry)
        //            .inDomain(cluster.clusterName + "-metrics")
        //            .build()
        //        reporter.start()

        return performQuery(cluster, cql)
    }

    private fun performQuery(cluster: Cluster, cql: String): ResultSet {
        return cluster.use {
            it.newSession().use { session ->
                session.execute(cql)
            }
        }
    }
}