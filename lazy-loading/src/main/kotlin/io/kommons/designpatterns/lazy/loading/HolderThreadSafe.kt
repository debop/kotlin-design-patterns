package io.kommons.designpatterns.lazy.loading

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * Thread safe하게 지연 생성을 수행
 */
class HolderThreadSafe {

    companion object: KLogging()

    init {
        log.info { "HolderThreadSafe created." }
    }

    private lateinit var heavy: Heavy

    @Synchronized
    fun getHeavy(): Heavy {
        if (!this::heavy.isInitialized) {
            heavy = Heavy()
        }
        return heavy
    }
}