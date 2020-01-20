package io.kommons

/**
 * 값 할당을 `value`를 처음 참조할 때 생성하도록 합니다.
 *
 * @author debop
 */
class LazyValue<T: Any>(private inline val factory: () -> T): AbstractValueObject() {

    @Volatile
    private var initialized: Boolean = false

    val isInitialized: Boolean get() = initialized

    val value: T by lazy { factory.invoke().apply { initialized = true } }

    inline fun <S: Any> map(crossinline mapper: (T) -> S): LazyValue<S> =
        LazyValue { mapper(value) }

    inline fun <S: Any> flatMap(crossinline mapper: (T) -> LazyValue<S>): LazyValue<S> =
        LazyValue { mapper.invoke(value).value }


    override fun hashCode(): Int = value.hashCode()

    override fun equalProperties(other: Any): Boolean {
        return other is LazyValue<*> && value == other.value
    }

    override fun buildStringHelper(): ToStringBuilder {
        return super.buildStringHelper()
            .add("value", value)
    }
}