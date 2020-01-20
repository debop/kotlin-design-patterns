package io.kommons.testcontainers.generic

import com.hazelcast.config.RestApiConfig
import com.hazelcast.spi.properties.GroupProperty
import com.hazelcast.spi.properties.HazelcastProperty
import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * Docker에서 실행되는 Hazelcast 서버
 *
 * ```kotlin
 * val hazelcastServer = HazelcastServer().apply { start() }
 * ```
 *
 * @author debop
 */
class HazelcastServer @JvmOverloads constructor(
    tag: String = DEFAULT_TAG,
    useDefaultPort: Boolean = false
): GenericContainer<HazelcastServer>("$IMAGE_NAME:$tag"), GenericServer {

    companion object: KLogging() {
        const val IMAGE_NAME = "hazelcast/hazelcast"
        const val DEFAULT_TAG = "3.11.1"

        const val HAZELCAST_NAME = "s3mock"
        const val HAZELCAST_PORT = 5701
    }

    private val enabledFeatures = HashSet<HazelcastProperty>()
    private val customProperties = HashSet<String>()
    private lateinit var config: RestApiConfig

    init {
        withExposedPorts(HAZELCAST_PORT)
        withLogConsumer(Slf4jLogConsumer(log))
        setWaitStrategy(Wait.forListeningPort())

        if (useDefaultPort) {
            exposeCustomPorts(HAZELCAST_PORT)
        }
    }

    override fun start() {
        super.start()
        writeToSystemProperties(HAZELCAST_NAME)
    }

    fun withHttpHealthCheck() = apply {
        enabledFeatures.add(GroupProperty.HTTP_HEALTHCHECK_ENABLED)
    }

    fun withRESTClient() = apply {
        enabledFeatures.add(GroupProperty.REST_ENABLED)
    }

    fun withRestApi(config: RestApiConfig) {
        this.config = config
    }

    fun withCustomProperty(property: String) = apply {
        customProperties.add(property)
    }

    override fun configure() {
        super.configure()

        var javaOpts = ""
        if (::config.isInitialized) {
            javaOpts += config.enabledGroups.joinToString(" ") { "-D${it.name}=true" }
        }

        javaOpts += " " + enabledFeatures.joinToString(" ") { "-D${it.name}=true" }
        val customProps = customProperties.joinToString(" ") { "-D$it" }

        log.debug(javaOpts)
        withEnv("JAVA_OPTS", "$javaOpts $customProps")
    }

    fun getRestBaseUrl(): String = "$url/hazelcast/rest"
}