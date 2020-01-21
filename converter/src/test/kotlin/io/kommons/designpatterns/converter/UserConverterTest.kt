package io.kommons.designpatterns.converter

import io.kommons.junit.jupiter.random.RandomValue
import io.kommons.junit.jupiter.random.RandomizedTest
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.RepeatedTest

/**
 * UserConverterTest
 */
@RandomizedTest
class UserConverterTest {

    private val converter = UserConverter()

    @RepeatedTest(5)
    fun `convert user to userDto`(@RandomValue user: User) {
        val converted = converter.convertToEntity(converter.convertToDto(user))
        converted shouldEqual user
    }

    @RepeatedTest(5)
    fun `convert userDto to user`(@RandomValue userDto: UserDto) {
        val converted = converter.convertToDto(converter.convertToEntity(userDto))
        converted shouldEqual userDto
    }

    @RepeatedTest(5)
    fun `convert user collections`(@RandomValue(type = User::class) users: List<User>) {
        val dtos = converter.convertToDtos(users)
        val converted = converter.convertToEntities(dtos)

        converted shouldContainSame users
    }

    @RepeatedTest(5)
    fun `convert userDto collections`(@RandomValue(type = UserDto::class) userDtos: List<UserDto>) {
        val users = converter.convertToEntities(userDtos)
        val converted = converter.convertToDtos(users)

        converted shouldContainSame userDtos
    }
}