package io.kommons.designpatterns.abstractdocument.domain

import io.kommons.designpatterns.abstractdocument.Document

/**
 * HasType
 *
 * @author debop
 * @since 28/09/2019
 */
interface HasType: Document {

    @JvmDefault
    fun getType(): String? = get(Property.TYPE.name) as? String

}