package io.kommons

/**
 * null 을 받을 수 없는 저장소에 특정 값을 저장할 때 사용합니다.
 *
 * @author debop
 */
data class ValueWrapper(val value: Any?): AbstractValueObject() {

    inline fun getOrElse(getter: () -> Any?): Any? = value ?: getter()

    fun getOrNull(): Any? = value

    override fun equalProperties(other: Any): Boolean = when (other) {
        is ValueWrapper -> value == other.value
        else            -> false
    }

    override fun buildStringHelper(): ToStringBuilder {
        return super.buildStringHelper()
            .add("value", value)
    }
}