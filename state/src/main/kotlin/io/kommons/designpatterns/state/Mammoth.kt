package io.kommons.designpatterns.state

/**
 * Mammoth has internal state that defines its behavior.
 */
class Mammoth {

    private var state: State = PeacefulState(this)

    /**
     * Makes time pass for the mammoth
     */
    fun timepasses() {
        when (state) {
            is PeacefulState -> changeStateTo(AngreState(this))
            else             -> changeStateTo(PeacefulState(this))
        }
    }

    fun observe() {
        this.state.observe()
    }

    private fun changeStateTo(newState: State) {
        this.state = newState
        this.state.onEtnerState()
    }

    override fun toString(): String = "The mammoth"
}