package io.kommons.designpatterns.eda

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class EventTest {

    val user = User("ilumator")

    @Test
    fun `get event type in UserCreatedEvent`() {
        val userCreatedEvent = UserCreatedEvent(user)
        userCreatedEvent.type shouldEqual UserCreatedEvent::class.java
    }

    @Test
    fun `get event type in UserUpdatedEvent`() {
        val userUpdatedEvent = UserUpdatedEvent(user)
        userUpdatedEvent.type shouldEqual UserUpdatedEvent::class.java
    }

}