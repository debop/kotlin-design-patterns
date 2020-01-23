package io.kommons.designpatterns.featuretoggle.pattern

import io.kommons.designpatterns.featuretoggle.User
import io.kommons.designpatterns.featuretoggle.UserGroup
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class TieredFeatureToggleVersionTest {

    companion object: KLogging()

    private val paidUser = User("Jamie Coder")
    private val freeUser = User("Alan Defect")
    private val service = TieredFeatureToggleVersion()

    @BeforeAll
    fun setup() {
        UserGroup.addUserToPaidGroup(paidUser)
        UserGroup.addUserToFreeGroup(freeUser)
    }

    @Test
    fun `get welcome message for paid user`() {
        service.getWelcomeMessage(paidUser) shouldEqual "You're amazing Jamie Coder. Thanks for paying for this awesome software."
    }

    @Test
    fun `get welcome message for free user`() {
        service.getWelcomeMessage(freeUser) shouldEqual "I suppose you can use this software."
    }

    @Test
    fun `is enhanced always true as tiered`() {
        service.isEnhanced.shouldBeTrue()
    }
}