package io.kommons.designpatterns.featuretoggle.pattern

import io.kommons.designpatterns.featuretoggle.User

interface Service {

    /**
     * Generates a welcome message for the passed user.
     *
     * @param user the [User] to be used if the message is to be personalised.
     * @return Generated string welcome message
     */
    fun getWelcomeMessage(user: User): String

    /**
     * Returns if the welcome message to be displayed will be the enhanced version.
     */
    val isEnhanced: Boolean
}