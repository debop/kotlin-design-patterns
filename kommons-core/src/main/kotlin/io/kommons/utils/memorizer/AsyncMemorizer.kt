package io.kommons.utils.memorizer

import java.util.concurrent.CompletionStage

/**
 * 비동기 함수 결과를 기록하여, 재시도 시에는 빠르게 결과를 응답할 수 있도록 해주는 Memorizer 입니다.
 *
 * @author debop
 */
interface AsyncMemorizer<in T, R>: (T) -> CompletionStage<R> {

    /**
     * 저장된 기존 수행 결과를 clear 합니다.
     */
    fun clear()

}