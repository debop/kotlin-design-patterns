package io.kommons.cache

import kotlinx.coroutines.Deferred

/**
 * Asynchronous Cache Provider using Kotlin Coroutines
 *
 * @author debop
 * @since 2017. 4. 29.
 * @see CacheProvider
 */
interface AsyncCacheProvider {

    val expiryInSeconds: Long get() = DEFAULT_EXPIRY_IN_SECODNS

    fun exists(key: Any): Deferred<Boolean>

    operator fun <T: Any> get(key: Any): Deferred<T?>

    operator fun set(key: Any, value: Any?): Deferred<Boolean> =
        set(key, value, expiryInSeconds)

    fun set(key: Any, value: Any?, expiryInSeconds: Long): Deferred<Boolean>

    fun setIfAbsent(key: Any, value: Any?): Deferred<Boolean> =
        setIfAbsent(key, value, expiryInSeconds)


    fun setIfAbsent(key: Any, value: Any?, expiryInSeconds: Long): Deferred<Boolean>

    fun evict(key: Any): Deferred<Boolean>

    fun evictAll(vararg keys: Any): Deferred<Boolean>

    fun clear(): Deferred<Boolean>
}

/**
 * Name을 가지는 [AsyncCacheProvider]
 */
interface NamedAsyncCache: AsyncCacheProvider {

    val name: String

}