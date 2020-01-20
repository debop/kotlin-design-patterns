package io.kommons.designpatterns.abstractdocument

/**
 * Document
 *
 * @author debop
 * @since 28/09/2019
 */
interface Document {

    operator fun set(key: String, value: Any?)

    operator fun get(key: String): Any?

    fun <T> children(key: String, constructor: (MutableMap<String, Any?>) -> T): Sequence<T>
}