package io.kommons.testcontainers.aws

import io.kommons.logging.KLogging
import io.kommons.testcontainers.GenericServer
import io.kommons.testcontainers.exposeCustomPorts
import io.kommons.testcontainers.writeToSystemProperties
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

/**
 * AWS S3를 가상으로 테스트할 수 있는 Mock Server 입니다.
 *
 * ```kotlin
 * // launch S3Server
 * val s3Server = S3Server().apply { start() }
 * ```
 *
 * @author debop
 */
class S3Server @JvmOverloads constructor(
    tag: String = DEFAULT_TAG,
    useDefaultPort: Boolean = false,
    val bucketName: String = DEFAULT_BUCKET_NAME
): GenericContainer<S3Server>("$IMAGE_NAME:$tag"), GenericServer {

    companion object: KLogging() {
        const val IMAGE_NAME = "adobe/s3mock"
        const val DEFAULT_TAG = "2.1.5"

        const val S3_NAME = "s3mock"
        const val S3_PORT = 9090
        const val DEFAULT_BUCKET_NAME = "test"
    }

    init {
        addEnv("initialBuckets", bucketName)
        withExposedPorts(S3_PORT)
        withLogConsumer(Slf4jLogConsumer(log))
        setWaitStrategy(Wait.forListeningPort())

        if (useDefaultPort) {
            exposeCustomPorts(S3_PORT)
        }
    }

    override fun start() {
        super.start()
        writeToSystemProperties(S3_NAME)
    }
}