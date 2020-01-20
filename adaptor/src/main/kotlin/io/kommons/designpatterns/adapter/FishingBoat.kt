package io.kommons.designpatterns.adapter

import io.kommons.logging.KLogging
import io.kommons.logging.debug

/**
 * FishingBoat
 *
 * @author debop
 * @since 19. 9. 18.
 */
class FishingBoat {

    companion object: KLogging()

    fun sail() {
        log.debug { "The fishing boat is sailing" }
    }
}