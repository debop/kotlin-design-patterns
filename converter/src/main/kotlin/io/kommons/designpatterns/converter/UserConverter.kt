package io.kommons.designpatterns.converter

/**
 * UserConverter
 */
class UserConverter: DomainConverter<User, UserDto>(
    toDto = { it.toDto() },
    toEntity = { it.toUser() }
)