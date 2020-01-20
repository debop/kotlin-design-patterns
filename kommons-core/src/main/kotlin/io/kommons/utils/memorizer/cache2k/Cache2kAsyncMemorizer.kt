package io.kommons.utils.memorizer.cache2k

import io.kommons.utils.memorizer.AsyncMemorizer
import org.cache2k.Cache
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

/**
 * [evaluator]을 실행 결과를 Cache2k의 cache에 저장하여, 같은 입력 값에 대해 빠르게 응답할 수 있도록 합니다.
 *
 * @param evaluator 실행할 비동기 함수
 * @return [AsyncMemorizer] 인스턴스
 */
fun <T, R> Cache<T, R>.asyncMemorizer(evaluator: (T) -> CompletionStage<R>): AsyncMemorizer<T, R> =
    Cache2kAsyncMemorizer(this, evaluator)

/**
 * 함수의 실행 결과를 캐시하여, 재 호출 시 캐시된 내용을 제공하도록 합니다.
 *
 * @param cache     결과를 저장할 Cache2k Cache instance
 * @param evaluator 실행할 비동기 함수
 * @return [AsyncMemorizer] 인스턴스
 */
class Cache2kAsyncMemorizer<T, R>(private val cache: Cache<T, R>,
                                  private val evaluator: (T) -> CompletionStage<R>): AsyncMemorizer<T, R> {

    override fun invoke(key: T): CompletionStage<R> {
        if (cache.containsKey(key)) {
            return CompletableFuture.completedFuture(cache.get(key))
        }

        val promise = CompletableFuture<R>()

        evaluator(key)
            .whenComplete { result, error ->
                if (error != null) {
                    promise.completeExceptionally(error)
                } else {
                    cache.put(key, result)
                    promise.complete(result)
                }
            }

        return promise
    }

    override fun clear() {
        cache.clear()
    }
}