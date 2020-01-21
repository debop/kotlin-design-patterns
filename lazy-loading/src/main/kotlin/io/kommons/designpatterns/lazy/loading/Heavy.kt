package io.kommons.designpatterns.lazy.loading

import io.kommons.logging.KLogging
import io.kommons.logging.info

/**
 * 생성에 많은 비용이 들어가는 클래스
 *
 * @author debop
 * @since 28/09/2019
 */
class Heavy {

    companion object: KLogging()

    init {
        log.info { "Creating Heavy ... " }

        Thread.sleep(1000)

        log.info { "... Heavy created" }
    }
}