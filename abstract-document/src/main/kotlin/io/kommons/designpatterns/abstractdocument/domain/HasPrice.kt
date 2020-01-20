package io.kommons.designpatterns.abstractdocument.domain

import io.kommons.designpatterns.abstractdocument.Document

/**
 * HasPrice
 *
 * @author debop
 * @since 28/09/2019
 */
interface HasPrice: Document {

    @JvmDefault
    fun getPrice(): Number? = get(Property.PRICE.name) as? Number
}