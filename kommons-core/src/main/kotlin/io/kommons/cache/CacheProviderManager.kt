package io.kommons.cache

const val DEFAULT_EXPIRY_IN_SECODNS: Long = 3600        // 1 hour

/**
 * [CacheProvider]를 관리하는 Manager 입니다.
 */
interface CacheProviderManager {

    /** 관리하는 Cache name의 컬렉션 */
    val cacheNames: Collection<String>

    /** 비동기 방식으로 관리하는 Cache name의 컬렉션 */
    val asyncCacheNames: Collection<String>

    /**
     * 동기 방식의 [CacheProvider] 얻기
     */
    fun getCache(): CacheProvider

    /**
     * 비동기 방식의 [AsyncCacheProvider] 얻기
     */
    fun getAsyncCache(): AsyncCacheProvider
}


