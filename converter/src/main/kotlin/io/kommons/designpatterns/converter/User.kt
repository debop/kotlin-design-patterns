package io.kommons.designpatterns.converter

/**
 * User
 */
data class User(val id: String,
                val firstName: String,
                val lastName: String,
                val isActive: Boolean = true)