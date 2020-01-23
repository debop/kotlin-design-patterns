package io.kommons.designpatterns.featuretoggle.pattern

import io.kommons.designpatterns.featuretoggle.User
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.Properties

class PropertiesFeatureToggleVersionTest {

    companion object: KLogging()

    @Test
    fun `non boolean properties`() {
        assertThrows<IllegalArgumentException> {
            val properties = Properties().apply { setProperty("enhancedWelcome", "Something") }
            PropertiesFeatureToggleVersion(properties)
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `feature turned on`(enhanced: Boolean) {
        val properties = Properties().apply { put("enhancedWelcome", enhanced) }
        val service = PropertiesFeatureToggleVersion(properties)

        service.isEnhanced shouldEqual enhanced

        val welcomeMessage = service.getWelcomeMessage(User("Jamie No Code"))
        when (enhanced) {
            true -> welcomeMessage shouldEqual "Welcome Jamie No Code. You're using the enhanced welcome message."
            else -> welcomeMessage shouldEqual "Welcome to the application."
        }
    }
}