package io.kommons.utils.memorizer

/**
 * 함수의 실행 결과를 캐시하여, 재 호출 시 캐시된 내용을 제공하도록 합니다.
 *
 * @param evaluator 수행할 함수
 * @return Memorizer<T, R> instance
 */
fun <T, R> inMemoryMemorizer(evaluator: (T) -> R): Memorizer<T, R> =
    InMemoryMemorizer(evaluator)

/**
 * 함수의 결과를 메모리에 보관하여, 같은 인자에 대해서는 함수를 실행하지 않고, 보관된 결과값을 반환하여, 재실행 시 비용을 절감합니다.
 *
 * @property evaluator 실제 수행할 함수
 */
class InMemoryMemorizer<in T, out R>(val evaluator: (T) -> R): Memorizer<T, R> {

    // 특정 입력값에 대한 결과를 캐시합니다.
    // Java의 ConcurrentHashMap을 사용하면 Deadlock 이 걸립니다.
    private val resultCache = LinkedHashMap<T, R>()

    override fun invoke(input: T): R {
        return resultCache.computeIfAbsent(input, evaluator)
    }

    /**
     * 저장된 기존 수행 결과를 clear 합니다.
     */
    override fun clear() {
        resultCache.clear()
    }
}
