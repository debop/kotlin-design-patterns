package io.kommons.designpatterns.roleobject

import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class RoleTest {

    @ParameterizedTest
    @EnumSource(value = Role::class)
    fun `instancing role`(role: Role) {
        val instance: CustomerRole? = role.instance()
        instance.shouldNotBeNull()
        instance shouldBeInstanceOf role.typeCast
    }
}