package io.kommons.designpatterns.eda.framework

/**
 * Handles the routing of {@link Event} messages to associated handlers. A {@link HashMap} is used
 * to store the association between events and their respective handlers.
 */
class EventDispatcher {

    private val handlers = mutableMapOf<Class<out Event>, Handler<out Event>>()

    /**
     * Links an {@link Event} to a specific {@link Handler}.
     *
     * @param eventType The {@link Event} to be registered
     * @param handler   The {@link Handler} that will be handling the {@link Event}
     */
    fun <E: Event> registerHandler(eventType: Class<E>, handler: Handler<E>) {
        handlers[eventType] = handler
    }

    /**
     * Dispatches an {@link Event} depending on it's type.
     *
     * @param event The {@link Event} to be dispatched
     */
    @Suppress("UNCHECKED_CAST")
    fun <E: Event> dispatch(event: E) {
        val handler = handlers[event.type] as? Handler<E>
        handler?.onEvent(event)
    }
}