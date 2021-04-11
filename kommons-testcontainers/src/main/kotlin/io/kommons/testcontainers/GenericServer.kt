package io.kommons.testcontainers

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports.Binding
import io.kommons.logging.KotlinLogging
import io.kommons.logging.info
import org.testcontainers.containers.ContainerState
import org.testcontainers.containers.GenericContainer

/**
 * Docker Container로 실행되는 서버의 기본정보를 표현합니다.
 *
 * @author debop
 */
interface GenericServer: ContainerState {

    @JvmDefault
    val port: Int
        get() = firstMappedPort

    @JvmDefault
    val url: String
        get() = "http://$host:$port"

    //    @Deprecated("Use url", replaceWith = ReplaceWith("url"))
    //    @JvmName("-deprecated_getUrl")
    //    fun getUrl(): String = "http://$host:$port"
}

internal const val SERVER_PREFIX = "testcontainers"

private val log = KotlinLogging.logger {}

/**
 * Docker Container의 exposed port를 지정한 port로 expose 하도록 합니다.
 * 이렇게 하지 않으면 Docker가 임의의 port number로 expose 합니다.
 *
 * @param T Server type
 * @param exposedPorts port numbers to exposed
 */
fun <T: GenericContainer<T>> GenericContainer<T>.exposeCustomPorts(vararg exposedPorts: Int) {
    val bindings = exposedPorts.map { PortBinding(Binding.bindPort(it), ExposedPort(it)) }
    if (bindings.isNotEmpty()) {
        withCreateContainerCmdModifier { cmd ->
            cmd.withPortBindings(bindings)
        }
    }
}

/**
 * 테스트를 위한 Server의 기본 정보를 System Property로 등록하여 Application 환경설정에서 사용할 수 있도록 합니다.
 *
 * ```properties
 * spring.redis.host = ${testcontainers.redis.host}
 * spring.redis.port = ${testcontainers.redis.port}
 * spring.redis.url = ${testcontainers.redis.url}
 * ```
 */
@JvmOverloads
fun <T: GenericServer> T.writeToSystemProperties(name: String, moreProps: Map<String, Any?> = emptyMap()) {
    log.info { "Setup Server properties ..." }

    System.setProperty("$SERVER_PREFIX.$name.host", this.host)
    System.setProperty("$SERVER_PREFIX.$name.port", this.port.toString())
    System.setProperty("$SERVER_PREFIX.$name.url", this.url)

    moreProps.forEach { (key, value) ->
        value?.run {
            System.setProperty("$SERVER_PREFIX.$name.$key", this.toString())
        }
    }

    val message = buildString {
        appendln("""
                |    
                |   Start ${name.capitalize()} Server:
                |        $SERVER_PREFIX.$name.host = $host
                |        $SERVER_PREFIX.$name.port = $port
                |        $SERVER_PREFIX.$name.url = $url
                """.trimMargin())

        moreProps.forEach { (key, value) ->
            value?.run {
                appendln("        $SERVER_PREFIX.$name.$key = $this")
            }
        }
    }

    log.info { message }
}