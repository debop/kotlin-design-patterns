package io.kommons.functional

/**
 * 지정한 함수를 curry 함소로 변환합니다.
 *
 * ```kotlin
 *
 * val func : (Int, Int) -> Int = { a, b -> a + b }
 * val curried = func.curried()
 *
 * val result = curried(1)(2)  // returns 3
 *
 * val func1 = func.curried()(1)
 * val result = func1(3)       // returns 4
 * ```
 *
 * @receiver Function2<T1, T2, R>
 * @return (T1) -> (T2) -> R
 */
fun <T1, T2, R> Function2<T1, T2, R>.curried(): (T1) -> (T2) -> R =
    { t1: T1 -> { t2: T2 -> this(t1, t2) } }

/**
 * 지정한 함수를 curry 함소로 변환합니다.
 *
 * ```kotlin
 *
 * val func : (Int, Int, Int) -> Int = { a, b, c -> a + b + c }
 * val curried = func.curried()
 *
 * val result = curried(1)(2)(3)  // returns 6
 *
 * val func2 = func.curried()(1)
 * val result = func2(3)(4)       // returns 1 + 3 + 4
 * ```
 *
 * @receiver Function2<T1, T2, R>
 * @return (T1) -> (T2) -> R
 */
fun <T1, T2, T3, R> Function3<T1, T2, T3, R>.curried(): (T1) -> (T2) -> (T3) -> R =
    { t1: T1 -> { t2: T2 -> { t3: T3 -> this(t1, t2, t3) } } }