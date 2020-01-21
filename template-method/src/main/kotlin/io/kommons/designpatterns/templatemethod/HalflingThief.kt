package io.kommons.designpatterns.templatemethod

/**
 * Halfling thief uses [StealingMethod] to steal.
 */
class HalflingThief(private var method: StealingMethod) {

    fun steal() {
        method.steal()
    }

    fun changeMethod(method: StealingMethod) {
        this.method = method
    }

}