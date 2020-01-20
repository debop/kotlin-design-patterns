package io.kommons.designpatterns.callback

import io.kommons.junit.jupiter.output.CaptureSystemOutput
import io.kommons.junit.jupiter.output.OutputCapture
import io.kommons.logging.KLogging
import io.kommons.logging.debug
import org.amshove.kluent.shouldContain
import org.junit.jupiter.api.Test

@CaptureSystemOutput
class CallbackTest {

    companion object: KLogging() {
        private const val CALLBACK_MSG = "Callback이 호출되었습니다."
    }

    val task = SimpleTask()

    @Test
    fun `execute with callback`(output: OutputCapture) {
        val callback = object: Callback {
            override fun call() {
                log.debug { CALLBACK_MSG }
            }
        }
        task.executeWithCallback(callback)
        output.capture() shouldContain CALLBACK_MSG
    }

    @Test
    fun `execute with null callback`() {
        task.executeWithCallback(null)
    }

    @Test
    fun `execute with lambda`(output: OutputCapture) {
        task.executeWithLambda {
            log.debug { CALLBACK_MSG }
        }
        output.capture() shouldContain CALLBACK_MSG
    }
}