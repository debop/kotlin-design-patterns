package io.kommons.designpatterns.converter

/**
 * UserDto
 */
data class UserDto(val email: String,
                   val firstName: String,
                   val lastName: String,
                   val isActive: Boolean = true)