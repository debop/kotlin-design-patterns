package io.kommons.designpatterns.acyclicvisitor

import io.kommons.logging.KLogging
import io.kommons.logging.debug

/**
 * ConfigureForUnixVisitor class implements zoom's visit method for Unix
 * manufacturer, unlike traditional visitor pattern, this class may selectively implement
 * visit for other modems.
 */
class ConfigureForUnixVisitor: ZoomVisitor {

    companion object: KLogging()

    override fun visit(zoom: Zoom) {
        log.debug { "$zoom used with Unix configurator." }
    }
}