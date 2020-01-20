package io.kommons.utils.memorizer

import java.util.concurrent.CompletionStage


fun <T, R> inMemoryAsyncMemorizer(evaluator: (T) -> CompletionStage<R>): AsyncMemorizer<T, R> =
    InMemoryAsyncMemorizer(evaluator)

/**
 * 함수의 결과를 메모리에 보관하여, 같은 인자에 대해서는 함수를 실행하지 않고, 보관된 결과값을 반환하여, 재실행 시 비용을 절감합니다.
 *
 * @property evaluator 실제 수행할 함수
 * @author debop
 */
class InMemoryAsyncMemorizer<T, R>(val evaluator: (T) -> CompletionStage<R>): AsyncMemorizer<T, R> {

    // 특정 입력값에 대한 결과를 캐시합니다.
    // Java의 ConcurrentHashMap을 사용하면 Deadlock 이 걸립니다.
    private val resultCache = LinkedHashMap<T, CompletionStage<R>>()


    override fun invoke(x: T): CompletionStage<R> {
        return resultCache.computeIfAbsent(x, evaluator)
    }

    /**
     * 저장된 기존 수행 결과를 clear 합니다.
     */
    override fun clear() {
        resultCache.clear()
    }
}