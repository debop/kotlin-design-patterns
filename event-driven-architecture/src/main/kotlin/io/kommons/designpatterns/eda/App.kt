package io.kommons.designpatterns.eda

import io.kommons.designpatterns.eda.framework.EventDispatcher

/**
 * An event-driven architecture (EDA) is a framework that orchestrates behavior around the
 * production, detection and consumption of events as well as the responses they evoke. An event is
 * any identifiable occurrence that has significance for system hardware or software.
 *
 * <p>The example below uses an {@link EventDispatcher} to link/register {@link Event} objects to
 * their respective handlers once an {@link Event} is dispatched, it's respective handler is invoked
 * and the {@link Event} is handled accordingly.
 */
class App

/**
 * Once the {@link EventDispatcher} is initialised, handlers related to specific events have to be
 * made known to the dispatcher by registering them. In this case the {@link UserCreatedEvent} is
 * bound to the UserCreatedEventHandler, whilst the {@link UserUpdatedEvent} is bound to the
 * {@link UserUpdatedEventHandler}. The dispatcher can now be called to dispatch specific events.
 * When a user is saved, the {@link UserCreatedEvent} can be dispatched. On the other hand, when a
 * user is updated, {@link UserUpdatedEvent} can be dispatched.
 */
fun main() {
    val dispatcher = EventDispatcher().apply {
        registerHandler(UserCreatedEvent::class.java, UserCreatedEventHandler())
        registerHandler(UserUpdatedEvent::class.java, UserUpdatedEventHandler())
    }

    val user = User("ilumator")
    dispatcher.dispatch(UserCreatedEvent(user))
    dispatcher.dispatch(UserUpdatedEvent(user))
}