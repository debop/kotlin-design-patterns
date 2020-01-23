package io.kommons.designpatterns.featuretoggle.pattern

import io.kommons.designpatterns.featuretoggle.User
import java.util.Properties

class PropertiesFeatureToggleVersion(properties: Properties): Service {

    private var enhanced: Boolean

    init {
        try {
            enhanced = properties["enhancedWelcome"]!! as Boolean
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid Enhancement Settings pProvided.")
        }
    }


    override fun getWelcomeMessage(user: User): String = when {
        isEnhanced -> "Welcome ${user.name}. You're using the enhanced welcome message."
        else       -> "Welcome to the application."
    }

    override val isEnhanced: Boolean
        get() = enhanced

}