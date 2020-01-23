package io.kommons.designpatterns.featuretoggle

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserGroupTest {

    companion object: KLogging()

    @Test
    fun `add user to free group`() {
        val user = User("Free User")
        UserGroup.addUserToFreeGroup(user)
        UserGroup.isPaid(user).shouldBeFalse()
    }

    @Test
    fun `add user to paid group`() {
        val user = User("Paid User")
        UserGroup.addUserToPaidGroup(user)
        UserGroup.isPaid(user).shouldBeTrue()
    }

    @Test
    fun `add user to paid when on free`() {
        val user = User("Paid User 2")
        UserGroup.addUserToFreeGroup(user)

        assertThrows<IllegalArgumentException> {
            UserGroup.addUserToPaidGroup(user)
        }
    }

    @Test
    fun `add user to free when on paid`() {
        val user = User("Free User 2")
        UserGroup.addUserToPaidGroup(user)

        assertThrows<IllegalArgumentException> {
            UserGroup.addUserToFreeGroup(user)
        }
    }
}