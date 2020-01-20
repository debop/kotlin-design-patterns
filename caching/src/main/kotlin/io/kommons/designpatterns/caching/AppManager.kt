package io.kommons.designpatterns.caching

import io.kommons.designpatterns.caching.CachingPolicy.AROUND
import io.kommons.designpatterns.caching.CachingPolicy.ASIDE
import io.kommons.designpatterns.caching.CachingPolicy.BEHIND
import io.kommons.designpatterns.caching.CachingPolicy.THROUGH
import kotlin.concurrent.thread

/**
 * AppManager
 *
 * @author debop
 */
object AppManager {

    private var cachingPolicy: CachingPolicy = THROUGH

    fun initDb(useMongoDb: Boolean = false) {
        if (useMongoDb) {
            try {
                DbManager.connect()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            DbManager.createVirtualDb()
        }
    }

    fun initCachingPolicy(policy: CachingPolicy) {
        cachingPolicy = policy
        if (cachingPolicy == BEHIND) {
            Runtime.getRuntime().addShutdownHook(thread(start = false) { CacheStore.flushCache() })
        }
        CacheStore.clearCache()
    }

    fun initCacheCapacity(capacity: Int) {
        CacheStore.initCapacity(capacity)
    }

    fun find(userId: String): UserAccount? {
        return when (cachingPolicy) {
            in listOf(THROUGH, AROUND) -> CacheStore.readThrough(userId)
            BEHIND                     -> CacheStore.readThroughWithWriteBackPolicy(userId)
            ASIDE                      -> findAndAside(userId)
            else                       -> null
        }
    }

    fun findAndAside(userId: String): UserAccount? {
        var userAccount = CacheStore.get(userId)
        if (userAccount == null) {
            userAccount = DbManager.readFromDb(userId)
            userAccount?.run { CacheStore.set(this) }
        }
        return userAccount
    }

    fun save(userAccount: UserAccount) {
        when (cachingPolicy) {
            THROUGH -> CacheStore.writeThrough(userAccount)
            AROUND  -> CacheStore.writeAround(userAccount)
            BEHIND  -> CacheStore.writeBehind(userAccount)
            ASIDE   -> saveAside(userAccount)
        }
    }

    fun saveAside(userAccount: UserAccount) {
        DbManager.updateDb(userAccount)
        CacheStore.invalidate(userAccount.userId)
    }

    fun printCacheContent(): String =
        CacheStore.print()
}