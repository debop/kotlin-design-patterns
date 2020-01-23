package io.kommons.designpatterns.featuretoggle

import io.kommons.logging.KLogging

data class User(val name: String)

/**
 * Contains the lists of users of different groups paid and free. Used to demonstrate the tiered
 * example of feature toggle. Allowing certain features to be available to only certain groups of
 * users.
 */
object UserGroup: KLogging() {

    private val freeGroup = arrayListOf<User>()
    private val paidGroup = arrayListOf<User>()

    fun addUserToFreeGroup(user: User) {
        if (paidGroup.contains(user)) {
            throw IllegalArgumentException("User already member of paid group.")
        }
        if (!freeGroup.contains(user)) {
            freeGroup.add(user)
        }
    }

    fun addUserToPaidGroup(user: User) {
        if (freeGroup.contains(user)) {
            throw IllegalArgumentException("User already member of free group.")
        }
        if (!paidGroup.contains(user)) {
            paidGroup.add(user)
        }
    }

    fun isPaid(user: User): Boolean {
        return paidGroup.contains(user)
    }
}