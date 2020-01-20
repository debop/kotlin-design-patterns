package io.kommons

/**
 * Serializable 이 아닌 객체를 Wrapping 하여 직렬화가 가능하도록 합니다.
 */
data class ObjectWrapper(val value: Any?): AbstractValueObject() {

    override fun equalProperties(other: Any): Boolean {
        return other is ObjectWrapper && value == other.value
    }

    override fun hashCode(): Int = value?.hashCode() ?: 0

    override fun buildStringHelper(): ToStringBuilder {
        return super.buildStringHelper()
            .add("value", value)
    }
}