package io.kommons.utils

import io.kommons.logging.KLogging
import io.kommons.logging.info
import io.kommons.logging.trace
import org.amshove.kluent.shouldBeGreaterThan
import org.amshove.kluent.shouldContain
import org.junit.jupiter.api.Test

class RuntimexTest {

    companion object: KLogging()

    @Test
    fun `컴퓨터 CPU Core 수를 얻습니다`() {
        log.trace { "CPU Core = ${Runtimex.availableProcessors}" }
        Runtimex.availableProcessors shouldBeGreaterThan 1
    }

    @Test
    fun `가용 메모리 얻기`() {
        log.trace { "Available Memory = ${Runtimex.availableMemory} bytes" }
        Runtimex.availableMemory shouldBeGreaterThan 0
    }

    @Test
    fun `가용 메모리 Percentage 계산`() {
        log.trace { "Available Memory Percentage = ${Runtimex.availableMemoryPercent} %" }
        Runtimex.availableMemoryPercent shouldBeGreaterThan 0.0
    }

    @Test
    fun `Free 메모리 얻기`() {
        log.trace { "Free Memory = ${Runtimex.freeMemory} bytes" }
        Runtimex.freeMemory shouldBeGreaterThan 0
    }

    @Test
    fun `Free 메모리 Percentage 계산`() {
        log.trace { "Free Memory Percentage = ${Runtimex.freeMemoryPercent} %" }
        Runtimex.freeMemoryPercent shouldBeGreaterThan 0.0
    }


    @Test
    fun `add shutdown hook`() {
        Runtimex.addShutdownHook {
            log.info { "JVM Shutdown gracefully." }
        }
    }

    @Test
    fun `run process and capture output`() {
        val process = ProcessBuilder("bash", "-c", "ls").start()
        val result = Runtimex.run(process)

        log.trace { "process result=$result" }
        result.out shouldContain "build.gradle.kts"
    }

}