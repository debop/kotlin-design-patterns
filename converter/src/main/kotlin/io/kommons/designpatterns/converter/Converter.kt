package io.kommons.designpatterns.converter

/**
 * Converter
 */
interface Converter<T, U> {

    fun convert(source: T): U

}

fun <T, U> converter(block: (T) -> U): Converter<T, U> {
    return object: Converter<T, U> {
        override fun convert(source: T): U = block.invoke(source)
    }
}