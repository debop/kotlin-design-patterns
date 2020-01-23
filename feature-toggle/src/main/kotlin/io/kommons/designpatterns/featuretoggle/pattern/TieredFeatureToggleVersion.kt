package io.kommons.designpatterns.featuretoggle.pattern

import io.kommons.designpatterns.featuretoggle.User
import io.kommons.designpatterns.featuretoggle.UserGroup

class TieredFeatureToggleVersion: Service {

    override fun getWelcomeMessage(user: User): String = when {
        UserGroup.isPaid(user) -> "You're amazing ${user.name}. Thanks for paying for this awesome software."
        else                   -> "I suppose you can use this software."
    }

    override val isEnhanced: Boolean = true

}