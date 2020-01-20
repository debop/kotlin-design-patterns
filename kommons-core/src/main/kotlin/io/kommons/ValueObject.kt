package io.kommons

import java.io.Serializable

/**
 * 값 형식의 데이터를 표현하는 클래스의 기본 인터페이스입니다.
 */
interface ValueObject: Serializable

/**
 * 값 형식의 데이터를 표현하는 [ValueObject]의 추상 클래스
 */
abstract class AbstractValueObject: ValueObject {

    /** Class의 고유성을 표현하는 속성들이 같은지 비교한다 */
    protected abstract fun equalProperties(other: Any): Boolean

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false

        return equalProperties(other)
    }

    override fun hashCode(): Int = System.identityHashCode(this)

    override fun toString(): String = buildStringHelper().toString()

    open fun toString(limit: Int): String = buildStringHelper().toString(limit)

    protected open fun buildStringHelper(): ToStringBuilder = ToStringBuilder(this)

}