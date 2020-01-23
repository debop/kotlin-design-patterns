package io.kommons.designpatterns.eda

import io.kommons.designpatterns.eda.framework.Event

class UserCreatedEvent(val user: User): Event

class UserUpdatedEvent(val user: User): Event