package io.kommons.cache

import io.kommons.logging.KLogging
import org.cache2k.Cache
import org.cache2k.Cache2kBuilder
import org.cache2k.configuration.Cache2kConfiguration

/**
 * Cache2k Cache를 제공합니다.
 *
 * @author debop
 */
object Cache2kProvider: KLogging() {

    const val DEFAULT_ENTRY_CAPACITY = 10_000L

    val caches: MutableMap<String, Cache<*, *>> = LinkedHashMap()

    /**
     * Cache2k 용 Cache를 제공합니다.
     *
     * @param config Cache2k용 설정정보
     * @return Cache2k [Cache] instance
     */
    @Suppress("UNCHECKED_CAST")
    fun <K, V> getCache(config: Cache2kConfiguration<K, V>): Cache<K, V> {
        return caches.computeIfAbsent(config.name) {
            buildCache2k(config)
        } as Cache<K, V>
    }

    /**
     * Cache2k 용 Cache를 제공합니다.
     *
     * @param K         cache key type
     * @param V         cache value type
     * @param name      캐시 명
     * @param entryCapacity  캐시 용량 (기본: 10_000)
     * @return Cache2k [Cache] instance
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified K, reified V> getCache(name: String, entryCapacity: Long = DEFAULT_ENTRY_CAPACITY): Cache<K, V> {
        return caches.computeIfAbsent(name) { buildCache2k<K, V>(it, entryCapacity) } as Cache<K, V>
    }

    /**
     * Cache2k의 Cache를 생성합니다.
     *
     * @param config Cache2k configuration
     * @return Cache2k [Cache] instance
     */
    fun <K, V> buildCache2k(config: Cache2kConfiguration<K, V>): Cache<K, V> {
        return Cache2kBuilder.of(config).build()
    }

    /**
     * Cache2k의 Cache를 생성합니다.
     *
     * @param name          캐시 이름
     * @param entryCapacity 캐시 용량 (기본: 10_000)
     * @return Cache2k [Cache] instance
     */
    @JvmOverloads
    inline fun <reified K, reified V> buildCache2k(name: String, entryCapacity: Long = DEFAULT_ENTRY_CAPACITY): Cache<K, V> {
        val config = Cache2kConfiguration
            .of(K::class.java, V::class.java)
            .also {
                it.name = name
                it.isBoostConcurrency = true
                it.isEternal = true
                it.entryCapacity = entryCapacity
                it.isPermitNullValues = true
                it.maxRetryInterval
            }
        return Cache2kBuilder.of(config).build()
    }

}