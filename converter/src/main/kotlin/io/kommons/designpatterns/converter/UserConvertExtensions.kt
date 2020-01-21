package io.kommons.designpatterns.converter


fun User.toDto(): UserDto = UserDto(id, firstName, lastName, isActive)

fun UserDto.toUser(): User = User(email, firstName, lastName, isActive)