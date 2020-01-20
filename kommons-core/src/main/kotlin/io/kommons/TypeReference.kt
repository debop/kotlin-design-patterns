package io.kommons

import java.lang.reflect.GenericArrayType
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import java.lang.reflect.WildcardType
import kotlin.reflect.KClass


fun <T: Any> typeRef(): TypeReference<T> = object: TypeReference<T>() {}
fun <T: Any> fullType(): TypeReference<T> = object: TypeReference<T>() {}

abstract class TypeReference<T> protected constructor() {

    val type: Type by lazy {
        this.javaClass.genericSuperclass.let { superClass ->
            if (superClass is Class<*>) {
                throw java.lang.IllegalArgumentException("Internal error: TypeReference constructed without actual type information")
            }
            (superClass as ParameterizedType).actualTypeArguments[0]
        }
    }

    val forClass: Class<out Any> by lazy { type.erasedType() }
}

@Suppress("UNCHECKED_CAST")
fun Type.erasedType(): Class<out Any> = when (this) {
    is Class<*>          -> this as Class<Any>
    is ParameterizedType -> this.rawType.erasedType()
    is GenericArrayType  -> {
        val elementType = this.genericComponentType.erasedType()
        val testArray = java.lang.reflect.Array.newInstance(elementType, 0)
        testArray::class.java
    }
    is TypeVariable<*>   -> error("지원되지 않는 수형입니다.")
    is WildcardType      -> this.upperBounds[0].erasedType()
    else                 -> error("지원되지 않는 수형힙니다.")
}

fun <T: Any> KClass<T>.isAssignableFrom(other: Type): Boolean =
    if (this.java == other) true
    else this.java.isAssignableFrom(other.erasedType())

fun Class<*>.isAssignableFrom(other: Type): Boolean =
    if (this == other) true
    else this.isAssignableFrom(other.erasedType())

fun <T: Any> Class<*>.isAssignableFrom(other: KClass<T>): Boolean =
    if (this == other.java) true
    else this.isAssignableFrom(other.java)

fun <T: Any> KClass<T>.isAssignableFrom(other: Class<*>): Boolean =
    if (this.java == other) true
    else this.java.isAssignableFrom(other)


fun <T: Any, O: Any> KClass<T>.isAssignableFrom(other: KClass<O>): Boolean =
    if (this.java == other.java) true
    else this.java.isAssignableFrom(other.java)


fun Type.isAssignableFrom(other: Type): Boolean =
    if (this == other) true
    else this.erasedType().isAssignableFrom(other.erasedType())

fun Type.isAssignableFrom(other: Class<*>): Boolean =
    if (this == other) true
    else this.erasedType().isAssignableFrom(other)

fun Type.isAssignableFrom(other: KClass<*>): Boolean =
    this.erasedType().isAssignableFrom(other.java)
