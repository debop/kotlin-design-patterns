package io.kommons.designpatterns.chain

class Request(val requestType: RequestType, val requestDescription: String) {

    private var handled: Boolean = false

    fun markHandled() {
        handled = true
    }

    val isHandled: Boolean get() = handled

    override fun toString(): String = requestDescription
}