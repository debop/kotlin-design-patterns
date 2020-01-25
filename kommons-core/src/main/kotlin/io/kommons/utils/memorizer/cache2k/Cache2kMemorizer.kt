package io.kommons.utils.memorizer.cache2k

import io.kommons.utils.memorizer.Memorizer
import org.cache2k.Cache

/**
 * 함수의 실행 결과를 캐시하여, 재 호출 시 캐시된 내용을 제공하도록 합니다.
 *
 * @receiver Cache<T, R>  결과를 저장할 Cache2k Cache instance
 * @param evaluator 실행할 함수
 * @return Memorizer<T, R>
 */
fun <T, R> Cache<T, R>.memorizer(evaluator: (T) -> R): Memorizer<T, R> = Cache2kMemorizer(this, evaluator)

/**
 * 함수의 결과를 메모리에 보관하여, 같은 인자에 대해서는 함수를 실행하지 않고, 보관된 결과값을 반환하여, 재실행 시 비용을 절감합니다.
 *
 * @author debop
 */
class Cache2kMemorizer<in T, R>(private val cache: Cache<T, R>,
                                private val evaluator: (T) -> R): Memorizer<T, R> {


    override fun invoke(key: T): R {
        if (!cache.containsKey(key)) {
            val result = evaluator(key)
            cache.putIfAbsent(key, result)
        }
        return cache[key]!!
    }

    override fun clear() {
        cache.clear()
    }

    override fun put(key: T, value: R) {
        cache.put(key, value)
    }
}