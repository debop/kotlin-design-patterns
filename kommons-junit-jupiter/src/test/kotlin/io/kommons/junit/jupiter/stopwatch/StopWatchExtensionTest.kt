package io.kommons.junit.jupiter.stopwatch

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle

@TestInstance(Lifecycle.PER_METHOD)
@StopWatchTest
class StopWatchExtensionTest {

    @Test
    fun `테스트 후 실행시간을 로그애 씁니다`() {
        Thread.sleep(10)
    }

    @StopWatchTest
    @Test
    fun `메소드 별로 실행 시간을 측정합니다`() {
        Thread.sleep(1)
    }
}