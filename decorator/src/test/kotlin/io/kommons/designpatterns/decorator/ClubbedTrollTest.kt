package io.kommons.designpatterns.decorator

import io.mockk.spyk
import io.mockk.verify
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Test

/**
 * ClubbedTrollTest
 */
class ClubbedTrollTest {

    @Test
    fun `decorate troll`() {

        val simpleTroll = spyk(SimpleTroll())

        val clubbed = ClubbedTroll(simpleTroll)

        clubbed.getAttackPower() shouldEqualTo 20

        clubbed.attach()
        verify(exactly = 1) { simpleTroll.attach() }

        clubbed.fleeBattle()
        verify(exactly = 1) { simpleTroll.fleeBattle() }
    }
}