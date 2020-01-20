package io.kommons

import io.kommons.Option.None
import io.kommons.Option.Some
import java.io.Serializable

/**
 * Java Optional, Scala Option 에 대응하는 클래스
 *
 * @author debop
 */
sealed class Option<out T: Any>: Serializable {

    /**
     * [Option]이 아무 값도 가지지 않음을 표현합니다.
     */
    object None: Option<Nothing>()

    /**
     * Java Optional에 실제 값이 있는 경우는 [Some]으로, 값이 없는 empty 상태라면 [None]으로 표현합니다.
     */
    data class Some<out T: Any>(val value: T): Option<T>()

    val isPresent: Boolean get() = this != None

    inline fun <R: Any> map(transform: (T) -> R): Option<R> = when (this) {
        is Some -> Some(transform(value))
        None    -> None
    }

    fun ofNullable(): T? = when {
        this is Some -> value
        this == None -> null
        else         -> null
    }
}

/**
 * 특정 객체를 [Option] 로 변환합니다.
 */
fun <T: Any> T?.toOption(): Option<T> = this?.run { Some(this) } ?: None

/**
 * [Option] 의 값을 반환합니다 값이 없다면 지정된 기본값을 반환합니다.
 */
fun <T: Any> Option<T>.orElse(value: () -> T): T = this.ofNullable() ?: value()

inline fun <T: Any> Iterable<Option<T>>.forEach(action: (T) -> Unit): Unit =
    this.mapNotNull { it.ofNullable() }.forEach(action)

inline fun <T: Any, R: Any> Iterable<Option<T>>.flatMap(mapper: (T) -> R): List<R> =
    this.mapNotNull { it.ofNullable() }.map(mapper)