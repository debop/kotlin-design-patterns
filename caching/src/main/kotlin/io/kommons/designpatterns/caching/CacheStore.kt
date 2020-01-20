package io.kommons.designpatterns.caching

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import org.cache2k.Cache
import org.cache2k.Cache2kBuilder

/**
 * CacheStore
 *
 * @author debop
 * @since 24/09/2019
 */
object CacheStore: KLogging() {

    private lateinit var cache: Cache<String, UserAccount>
    private var cacheCapacity: Int = 100

    fun initCapacity(capacity: Int = 100) {
        cacheCapacity = capacity
        cache = Cache2kBuilder
            .of(String::class.java, UserAccount::class.java)
            .eternal(true)
            .storeByReference(false)
            .entryCapacity(cacheCapacity.toLong())
            .build()
    }


    fun readThrough(userId: String): UserAccount? {
        return if (cache.containsKey(userId)) {
            log.debug { "# Cache Hit!" }
            cache[userId]
        } else {
            log.debug { "# Cache Miss!" }
            DbManager.readFromDb(userId)?.apply { set(this) }
        }
    }

    fun writeThrough(userAccount: UserAccount) {
        if (cache.containsKey(userAccount.userId)) {
            DbManager.updateDb(userAccount)
        } else {
            DbManager.writeToDb(userAccount)
        }
        set(userAccount)
    }

    fun writeAround(userAccount: UserAccount) {
        if (cache.containsKey(userAccount.userId)) {
            DbManager.updateDb(userAccount)
            invalidate(userAccount.userId)              // Cache에서 오래된 버전은 삭제한다.
        } else {
            DbManager.writeToDb(userAccount)
        }
    }

    /**
     * Get user account using read-through cache with write-back policy
     */
    fun readThroughWithWriteBackPolicy(userId: String): UserAccount? {
        return if (cache.containsKey(userId)) {
            log.debug { "# Cache Hit!" }
            cache[userId]
        } else {
            log.debug { "# Cache Miss!" }
            val userAccount = DbManager.readFromDb(userId)

            userAccount?.also {
                // cache 용량이 찼으면, DB에 upsert를 수행한다
                // ...

                set(it)
            }
        }
    }

    fun writeBehind(userAccount: UserAccount) {
        if (cache.entries().count() == cacheCapacity && !cache.containsKey(userAccount.userId)) {
            log.debug { "# Cache is FULL! Write LRU data to DB..." }
            val toBeWrittenDb = cache.entries().last().value
            DbManager.upsertDb(toBeWrittenDb)
            cache.remove(toBeWrittenDb.userId)
        }

        set(userAccount)
    }

    fun clearCache() {
        if (this::cache.isInitialized) {
            cache.clear()
        }
    }

    fun flushCache() {
        log.debug { "# flush cache ..." }
        // cache 에 있는 정보를 DB에 모두 Upsert 한다
        if (this::cache.isInitialized) {
            cache.entries().forEach {
                DbManager.upsertDb(it.value)
            }
        }
    }

    fun get(userId: String): UserAccount? {
        return cache.get(userId)
    }

    fun set(userAccount: UserAccount) {
        cache.put(userAccount.userId, userAccount)
    }

    fun invalidate(userId: String) {
        cache.remove(userId)
    }

    fun print(): String = buildString {
        append("\n--CACHE CONTENT--\n")
        cache.entries().forEach { entry ->
            appendln(entry.value)
        }
        appendln("----")
    }
}
