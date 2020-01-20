package io.kommons

import java.io.Serializable

/**
 * Business Entity의 toString()을 손쉽게 설정할 수 있게 해주는 Builder 입니다.
 *
 * @see AbstractValueObject
 *
 * @author debop
 */
class ToStringBuilder(val className: String): Serializable {

    constructor(obj: Any): this(obj.javaClass.simpleName)

    companion object {
        @JvmStatic
        fun of(className: String): ToStringBuilder = ToStringBuilder(className)

        @JvmStatic
        fun of(obj: Any): ToStringBuilder = ToStringBuilder(obj)
    }

    init {
        className.assertNotBlank("className")
    }

    private val map = LinkedHashMap<String, Any?>()
    private lateinit var cachedToString: String

    private fun toStringValue(limit: Int): String {
        if (!::cachedToString.isInitialized) {
            val props = map.entries.joinToString(separator = ",", limit = limit) { "${it.key}=${it.value.asString()}" }
            cachedToString = "$className($props)"
        }
        return cachedToString
    }

    private fun Any?.asString(): String = this?.toString() ?: "<null>"

    fun add(name: String, value: Any?): ToStringBuilder = apply {
        map[name] = value?.asString()
    }

    override fun toString(): String = toStringValue(-1)

    /**
     * 문자열 변환 시 [limit] 크기만큼만 반환한다
     * @param limit 제한된 문자열 크기
     * @return toString 문자열
     */
    fun toString(limit: Int): String = toStringValue(limit)
}