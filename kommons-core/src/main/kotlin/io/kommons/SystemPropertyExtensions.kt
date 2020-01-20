package io.kommons

import io.kommons.logging.KotlinLogging

private val log = KotlinLogging.logger {}

/**
 * 시스템 설정 정보에 Kotlin 스타일로 접근할 수 있도록 해줍니다.
 *
 * ```kotlin
 *
 * // getter
 * val userDir = systemProperty["user.dir"]
 *
 * // setter
 * systemProperty["testcontainers.redis.port"] = 80.toString()
 * ```
 */
val systemProperty: GetterSetterOperation<String, String?> by lazy {
    GetterSetterOperation(
        getter = { key: String ->
            key.assertNotBlank("key")
            System.getProperty(key)
        },
        setter = { key: String, value: String? ->
            key.assertNotBlank("key")
            System.setProperty(key, value!!)
        }
    )
}