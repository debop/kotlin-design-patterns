package io.kommons.designpatterns.lazy.loading

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * 가장 간단한 지연 로딩 방식 (이 방식은 thread safe하지 않습니다)
 *
 * @author debop
 * @since 28/09/2019
 */
class HolderNaive {

    companion object: KLogging()

    init {
        log.info { "HolderNaive created." }
    }

    private lateinit var heavy: Heavy

    fun getHeavy(): Heavy {
        if (!this::heavy.isInitialized) {
            heavy = Heavy()
        }
        return heavy
    }
}