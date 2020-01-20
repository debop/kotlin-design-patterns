package io.kommons.designpatterns.caching

import io.kommons.designpatterns.caching.CachingPolicy.AROUND
import io.kommons.designpatterns.caching.CachingPolicy.ASIDE
import io.kommons.designpatterns.caching.CachingPolicy.BEHIND
import io.kommons.designpatterns.caching.CachingPolicy.THROUGH
import io.kommons.logging.KLogging
import io.kommons.logging.debug
import io.kommons.logging.info
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class CachingTest {

    companion object: KLogging()

    @BeforeAll
    fun beforeAll() {
        AppManager.initDb(false)
        AppManager.initCacheCapacity(3)
    }

    @Test
    fun `read-through and write-through`() {
        log.info { "# Caching policy: THROIUGHG" }
        AppManager.initCachingPolicy(THROUGH)

        val userAccount1 = UserAccount("001", "John", "He is a boy.")

        AppManager.save(userAccount1)
        log.debug { AppManager.printCacheContent() }
        AppManager.find("001")
        AppManager.find("001")
    }

    @Test
    fun `read through and write around strategy`() {
        log.info { "# Caching policy: AROUND" }
        AppManager.initCachingPolicy(AROUND)

        var userAccount2 = UserAccount("002", "John", "He is a girl.")

        AppManager.save(userAccount2)
        log.info { AppManager.printCacheContent() }
        AppManager.find("002")
        log.info { AppManager.printCacheContent() }

        userAccount2 = AppManager.find("002")!!
        userAccount2.userName = "John G."
        AppManager.save(userAccount2)
        log.info { AppManager.printCacheContent() }
        AppManager.find("002")
        log.info { AppManager.printCacheContent() }
        AppManager.find("002")
    }

    @Test
    fun `read through and write behind strategy`() {
        log.info { "# Caching policy: BEHIND" }
        AppManager.initCachingPolicy(BEHIND)

        val userAccount3 = UserAccount("003", "Adam", "He likes food.")
        val userAccount4 = UserAccount("004", "Rita", "She hates cats.")
        val userAccount5 = UserAccount("005", "Issac", "He is allegic to mustard.")

        AppManager.save(userAccount3)
        AppManager.save(userAccount4)
        AppManager.save(userAccount5)
        log.info { AppManager.printCacheContent() }
        AppManager.find("003")
        log.info { AppManager.printCacheContent() }
        val userAccount6 = UserAccount("006", "Yasha", "She is an only child.")
        AppManager.save(userAccount6)
        log.info { AppManager.printCacheContent() }
        AppManager.find("004")
        log.info { AppManager.printCacheContent() }
    }

    @Test
    fun `cache as aside strategy`() {
        log.info { "Caching policy: ASIDE" }
        AppManager.initCachingPolicy(ASIDE)
        log.info { AppManager.printCacheContent() }

        val userAccount3 = UserAccount("003", "Adam", "He likes food.")
        val userAccount4 = UserAccount("004", "Rita", "She hates cats.")
        val userAccount5 = UserAccount("005", "Issac", "He is allegic to mustard.")
        AppManager.save(userAccount3)
        AppManager.save(userAccount4)
        AppManager.save(userAccount5)

        log.info { AppManager.printCacheContent() }
        AppManager.find("003")
        log.info { AppManager.printCacheContent() }
        AppManager.find("004")
        log.info { AppManager.printCacheContent() }
    }
}