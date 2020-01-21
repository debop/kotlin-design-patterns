package io.kommons.designpatterns.chain

import io.kommons.junit.jupiter.output.CaptureSystemOutput
import io.kommons.junit.jupiter.output.OutputCapture
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldNotContain
import org.junit.jupiter.api.Test

@CaptureSystemOutput
class ChainTest {

    companion object: KLogging()

    private val king = OrcKing()

    @Test
    fun `handle orc command`(output: OutputCapture) {
        val request = Request(RequestType.DEFEND_CASTLE, "define castle")
        king.makeRequest(request)

        request.isHandled.shouldBeTrue()

        val outStr = output.toString()
        outStr shouldContain "Orc command"
        outStr shouldNotContain "Orc officer"
        outStr shouldNotContain "Orc soldier"
    }

    @Test
    fun `handle orc officer`(output: OutputCapture) {
        val request = Request(RequestType.TORTURE_PRISONER, "torture prisoner")
        king.makeRequest(request)

        request.isHandled.shouldBeTrue()

        val outStr = output.toString()
        outStr shouldNotContain "Orc command"
        outStr shouldContain "Orc officer"
        outStr shouldNotContain "Orc soldier"
    }

    @Test
    fun `handle orc soldier`(output: OutputCapture) {
        val request = Request(RequestType.COLLECT_TAX, "collect tax")
        king.makeRequest(request)

        request.isHandled.shouldBeTrue()

        val outStr = output.toString()
        outStr shouldNotContain "Orc command"
        outStr shouldNotContain "Orc officer"
        outStr shouldContain "Orc soldier"
    }

    @Test
    fun `multiple request`() {
        val requests = listOf(
            Request(RequestType.DEFEND_CASTLE, "Don't let the barbarians enter my castle!"),
            Request(RequestType.TORTURE_PRISONER, "Don't just stand there, tickle him!"),
            Request(RequestType.COLLECT_TAX, "Don't steal, the King hates competition ... ")
        )

        requests.forEach {
            king.makeRequest(it)
            it.isHandled.shouldBeTrue()
        }
    }
}