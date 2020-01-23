package io.kommons.designpatterns.eda.framework

import io.kommons.designpatterns.eda.User
import io.kommons.designpatterns.eda.UserCreatedEvent
import io.kommons.designpatterns.eda.UserCreatedEventHandler
import io.kommons.designpatterns.eda.UserUpdatedEvent
import io.kommons.designpatterns.eda.UserUpdatedEventHandler
import io.kommons.logging.KLogging
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Test

/**
 * Event Dispatcher unit tests to assert and verify correct event dispatcher behaviour
 */
class EventDispatcherTest {

    companion object: KLogging()

    /**
     * This unit test should register events and event handlers correctly with the event dispatcher
     * and events should be dispatched accordingly.
     */
    @Test
    fun `test event driver pattern`() {
        val dispatcher = spyk<EventDispatcher>()
        val userCreatedEventHandler = spyk<UserCreatedEventHandler>()
        val userUpdatedEventHandler = spyk<UserUpdatedEventHandler>()

        dispatcher.registerHandler(UserCreatedEvent::class.java, userCreatedEventHandler)
        dispatcher.registerHandler(UserUpdatedEvent::class.java, userUpdatedEventHandler)

        val user = User("ilumator")

        val userCreatedEvent = UserCreatedEvent(user)
        val userUpdatedEvent = UserUpdatedEvent(user)

        // fire a userCreatedEvent and verify that userCreatedEventHandler has been invoked.
        dispatcher.dispatch(userCreatedEvent)
        verify(exactly = 1) { userCreatedEventHandler.onEvent(userCreatedEvent) }
        verify(exactly = 1) { dispatcher.dispatch(userCreatedEvent) }

        // fire a userCreatedEvent and verify that userUpdatedEventHandler has been invoked.
        dispatcher.dispatch(userUpdatedEvent)
        verify(exactly = 1) { userUpdatedEventHandler.onEvent(userUpdatedEvent) }
        verify(exactly = 1) { dispatcher.dispatch(userUpdatedEvent) }
    }
}