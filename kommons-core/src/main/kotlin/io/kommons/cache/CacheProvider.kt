package io.kommons.cache


/**
 * 캐시 기능을 제공하는 Provider 입니다.
 *
 * @author debop
 */
interface CacheProvider {

    val expiryInSeconds: Long get() = DEFAULT_EXPIRY_IN_SECODNS

    fun exists(key: Any): Boolean

    operator fun <T: Any> get(key: Any): T?

    fun set(key: Any, value: Any?): Boolean =
        set(key, value, expiryInSeconds)

    fun set(key: Any, value: Any?, expiryInSeconds: Long): Boolean

    fun setIfAbsent(key: Any, value: Any?): Boolean =
        setIfAbsent(key, value, expiryInSeconds)

    fun setIfAbsent(key: Any, value: Any?, expiryInSeconds: Long): Boolean

    fun evict(key: Any): Boolean

    fun evictAll(vararg keys: Any): Boolean

    fun clear(): Boolean
}

/**
 * Name을 가지는 캐시 저장소를 나타내는 [CacheProvider]
 */
interface NamedCache: CacheProvider {

    val name: String

}