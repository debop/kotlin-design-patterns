package io.kommons.designpatterns.abstractdocument

/**
 * AbstractDocument
 *
 * @author debop
 * @since 28/09/2019
 */
abstract class AbstractDocument(private val properties: MutableMap<String, Any?>): Document {

    override fun set(key: String, value: Any?) {
        properties[key] = value
    }

    override fun get(key: String): Any? = properties[key]

    @Suppress("UNCHECKED_CAST")
    override fun <T> children(key: String, constructor: (MutableMap<String, Any?>) -> T): Sequence<T> {
        return sequenceOf(get(key))
            .filterNotNull()
            .mapNotNull { it as? List<MutableMap<String, Any?>> }
            .flatMap { list ->
                list.map { constructor(it) }.asSequence()
            }
    }

    override fun toString(): String = buildString {
        append(javaClass.name).append("[")
        properties.asSequence().joinTo(this, prefix = "[", postfix = "]") { "${it.key} : ${it.value}" }
        append("]")
    }
}