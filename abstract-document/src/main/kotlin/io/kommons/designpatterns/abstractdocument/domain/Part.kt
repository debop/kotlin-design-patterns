package io.kommons.designpatterns.abstractdocument.domain

import io.kommons.designpatterns.abstractdocument.AbstractDocument

/**
 * Part
 *
 * @author debop
 * @since 28/09/2019
 */
class Part(properties: MutableMap<String, Any?>): AbstractDocument(properties), HasType, HasModel, HasPrice