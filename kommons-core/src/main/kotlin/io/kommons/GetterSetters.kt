package io.kommons

/**
 * (T) -> V 를 나타내는 getter 기능을 표현한 interface 입니다.
 * @param K
 * @param V
 * @property getter Function1<K, V>
 */
interface GetterOperation<in K, out V> {
    val getter: (K) -> V
    operator fun get(key: K): V = getter(key)
}

fun <K, V> getterOperation(func: (K) -> V): GetterOperation<K, V> {
    return object: GetterOperation<K, V> {
        override val getter: (K) -> V
            get() = func
    }
}

interface SetterOperation<in K, in V> {
    val setter: (K, V) -> Unit
    operator fun set(key: K, value: V) {
        setter(key, value)
    }
}

fun <K, V> setterOperation(func: (K, V) -> Unit): SetterOperation<K, V> {
    return object: SetterOperation<K, V> {
        override val setter: (K, V) -> Unit
            get() = func
    }
}

/**
 * Getter, Setter 작업을 Kotlin 스타일로 표현할 수 있도록 했습니다
 *
 * @see systemProperty
 *
 * @param K  key type
 * @param V     value type
 * @property getter getter 함수
 * @property setter setter 함수
 */
class GetterSetterOperation<in K, V>(override val getter: (K) -> V,
                                     override val setter: (K, V) -> Unit)
    : GetterOperation<K, V>, SetterOperation<K, V>