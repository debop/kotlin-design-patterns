package io.kommons

import java.util.Objects
import java.util.Optional

/**
 * var 로 선언된 필드 중 non null 수형에 대해 초기화 값을 지정하고자 할 때 사용합니다.
 * 특히 `@Autowired`, `@Inject` val 수형에 사용하기 좋다.
 *
 * <pre>
 *   <code>
 *      @Inject val x: Repository = uninitialized()
 *   </code>
 * </pre>
 * @see lateinit
 * @see Delegates.nonNull
 */
@Suppress("UNCHECKED_CAST")
fun <T> uninitialized(): T = null as T

/**
 * 객체를 [Optional]로 변환합니다.
 */
fun <T: Any> T?.toOptional(): Optional<T> = Optional.ofNullable(this)

/**
 * [values]의 조합의 hash 값을 계산합니다.
 */
fun hashOf(vararg values: Any?): Int = Objects.hash(*values)

/**
 * 두 객체가 같은지 판단합니다. (둘 다 null이면 true를 반환합니다)
 */
fun areEquals(a: Any?, b: Any?): Boolean =
    (a == null && b == null) || (a != null && a == b)

/**
 * 두 객체가 모두 null인 경우는 false를 반환하고, array 인 경우에는 array 요소까지 비교합니다.
 */
fun nullSafeEquals(a: Any?, b: Any?): Boolean {
    if (a === b)
        return true

    if (a == null || b == null)
        return false

    if (a == b)
        return true

    if (a.javaClass.isArray && b.javaClass.isArray) {
        return arrayEquals(a, b)
    }

    return false
}

/**
 * 두 Object 가 같은 것인가 검사한다. Array인 경우도 검사할 수 있습니다.
 */
fun arrayEquals(a: Any, b: Any): Boolean {

    if (a is Array<*> && b is Array<*>) {
        return a.contentEquals(b)
    }
    if (a is BooleanArray && b is BooleanArray) {
        return a.contentEquals(b)
    }
    if (a is CharArray && b is CharArray) {
        return a.contentEquals(b)
    }
    if (a is ByteArray && b is ByteArray) {
        return a.contentEquals(b)
    }
    if (a is ShortArray && b is ShortArray) {
        return a.contentEquals(b)
    }
    if (a is IntArray && b is IntArray) {
        return a.contentEquals(b)
    }
    if (a is LongArray && b is LongArray) {
        return a.contentEquals(b)
    }
    if (a is FloatArray && b is FloatArray) {
        return a.contentEquals(b)
    }
    if (a is DoubleArray && b is DoubleArray) {
        return a.contentEquals(b)
    }
    return false
}

/**
 * 컬렉션의 모든 요소가 not null 인 경우에만 [block]을 수행합니다.
 */
infix fun <T: Any, R: Any> Collection<T?>.whenAllNotNull(block: (Collection<T>) -> R) {
    if (this.all { it != null }) {
        block(this.filterNotNull())
    }
}

/**
 * 컬렉션의 요소중 하나라도 null이 아니라면 [block]을 수행합니다.
 */
infix fun <T: Any, R: Any> Collection<T?>.whenAnyNotNull(block: (Collection<T>) -> R) {
    if (this.any { it != null }) {
        block(this.filterNotNull())
    }
}
