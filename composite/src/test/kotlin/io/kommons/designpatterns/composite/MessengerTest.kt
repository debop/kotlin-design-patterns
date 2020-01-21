package io.kommons.designpatterns.composite

import io.kommons.junit.jupiter.output.CaptureSystemOutput
import io.kommons.junit.jupiter.output.OutputCapture
import org.amshove.kluent.shouldContain
import org.junit.jupiter.api.Test

/**
 * MessengerTest
 */
@CaptureSystemOutput
class MessengerTest {

    val messenger = Messenger()

    @Test
    fun `verify message from Orcs`(output: OutputCapture) {
        val letterComposite = messenger.messageFromOrcs()
        letterComposite.print()

        output.toString() shouldContain "Where there is a whip there is a way."
    }

    @Test
    fun `verify message from Elves`(output: OutputCapture) {
        val letterComposite = messenger.messageFromElves()
        letterComposite.print()

        output.toString() shouldContain "Much wind pours from your mouth."
    }

}