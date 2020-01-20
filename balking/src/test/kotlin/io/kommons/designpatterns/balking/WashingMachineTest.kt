package io.kommons.designpatterns.balking

import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test
import java.time.Duration

class WashingMachineTest {

    private val fakeDelayProvider = FakeDelayProvider()

    @Test
    fun wash() {
        val washingMachine = WashingMachine(fakeDelayProvider)

        washingMachine.wash()
        washingMachine.wash()

        val machineStateGlobal = washingMachine.state

        runBlocking {
            fakeDelayProvider.task.invoke()
        }

        machineStateGlobal shouldEqual WashingMachineState.WASHING
        washingMachine.state shouldEqual WashingMachineState.ENABLED
    }

    @Test
    fun `end of washing`() {
        val washingMachine = WashingMachine()
        washingMachine.wash()
        washingMachine.state shouldEqual WashingMachineState.ENABLED
    }

    class FakeDelayProvider: DelayProvider {

        lateinit var task: suspend () -> Unit

        override suspend fun executeAfterDelay(delayDuration: Duration, task: suspend () -> Unit) {
            this.task = task
        }
    }
}