package io.kommons.cache

/**
 * Name을 가지는 [CacheProvider]를 관리하는 Manager 입니다.
 *
 * @author debop
 * @since 2017. 5. 6.
 */
interface NamedCacheManager {

    /**
     * Synchronous CacheProvider
     */
    fun getCache(name: String): NamedCache

    /**
     * Asynchronous CacheProvider
     */
    fun getAsyncCache(name: String): NamedAsyncCache

    /**
     * 제공하는 동기식 캐시 저장소의 이름들
     */
    val cacheNames: Collection<String>

    /**
     * 제공하는 비동기 캐시 저장소의 이름들
     */
    val asyncCacheNames: Collection<String>

    /**
     * 동기방식의 캐시 저장소를 Clear 합니다.
     */
    fun clearCache()

    /**
     * 비동기방식의 캐시 저장소를 Clear 합니다.
     */
    fun clearAsyncCache()
}