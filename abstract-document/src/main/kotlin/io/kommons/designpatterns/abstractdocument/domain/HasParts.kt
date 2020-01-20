package io.kommons.designpatterns.abstractdocument.domain

import io.kommons.designpatterns.abstractdocument.Document

/**
 * HasParts
 *
 * @author debop
 * @since 28/09/2019
 */
interface HasParts: Document {

    @JvmDefault
    fun getParts(): Sequence<Part> =
        children(Property.PARTS.name) { Part(it) }
}