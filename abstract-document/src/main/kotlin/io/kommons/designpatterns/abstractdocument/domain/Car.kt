package io.kommons.designpatterns.abstractdocument.domain

import io.kommons.designpatterns.abstractdocument.AbstractDocument

/**
 * Car
 *
 * @author debop
 * @since 28/09/2019
 */
class Car(properties: MutableMap<String, Any?>): AbstractDocument(properties), HasModel, HasPrice, HasParts 