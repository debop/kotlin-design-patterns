package io.kommons.designpatterns.eda.framework

/**
 * A [Event] is an object with a specific type that is associated to a specific [Handler]
 */
interface Event {

    /**
     * Returns the message type as a {@link Class} object. In this example the message type is used to
     * handle events by their type.
     *
     * @return the message type as a {@link Class}.
     */
    @JvmDefault
    val type: Class<out Event>
        get() = javaClass

}