package io.kommons.designpatterns.eda

/**
 * This {@link User} class is a basic pojo used to demonstrate user data sent along with the {@link
 * UserCreatedEvent} and {@link UserUpdatedEvent} events.
 */
data class User(val username: String)