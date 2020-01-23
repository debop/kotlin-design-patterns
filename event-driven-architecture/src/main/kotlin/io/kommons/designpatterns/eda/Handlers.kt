package io.kommons.designpatterns.eda

import io.kommons.designpatterns.eda.framework.Handler
import io.kommons.logging.KLogging
import io.kommons.logging.info

class UserCreatedEventHandler: Handler<UserCreatedEvent> {

    companion object: KLogging()

    override fun onEvent(event: UserCreatedEvent) {
        log.info { "User [${event.user.username}] has been Created!" }
    }

}

class UserUpdatedEventHandler: Handler<UserUpdatedEvent> {

    companion object: KLogging()

    override fun onEvent(event: UserUpdatedEvent) {
        log.info { "User [${event.user.username}] has been Updated!" }
    }

}