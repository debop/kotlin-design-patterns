package io.kommons.designpatterns.abstractdocument.domain

import io.kommons.designpatterns.abstractdocument.Document

/**
 * HasModel
 *
 * @author debop
 * @since 28/09/2019
 */
interface HasModel: Document {

    @JvmDefault
    fun getModel(): String? = get(Property.MODEL.name) as? String
}