package io.kommons.designpatterns.servant

import io.kommons.logging.KLogging
import io.mockk.clearMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ServantTest {

    companion object: KLogging()

    private val royalty = mockk<Royalty>(relaxUnitFun = true)

    @BeforeEach
    fun setup() {
        clearMocks(royalty)
    }

    @Test
    fun `test feed`() {
        val servant = Servant("test")
        servant.feed(royalty)

        verify(exactly = 1) { royalty.getFed() }
        confirmVerified(royalty)
    }

    @Test
    fun `give wine`() {
        val servant = Servant("test")
        servant.giveWine(royalty)

        verify(exactly = 1) { royalty.getDrink() }
        confirmVerified(royalty)
    }

    @Test
    fun `give compliments`() {

        val servant = Servant("test")
        servant.giveCompliments(royalty)

        verify(exactly = 1) { royalty.receiveCompliments() }
        confirmVerified(royalty)
    }

    @Test
    fun `check if you will be hanged`() {
        val goodMoodRoyalty = mockk<Royalty>(relaxUnitFun = true)
        every { goodMoodRoyalty.getMood() } returns true

        val badMoodRoyalty = mockk<Royalty>(relaxUnitFun = true)
        every { badMoodRoyalty.getMood() } returns false

        val goodCompany = listOf(goodMoodRoyalty, goodMoodRoyalty, goodMoodRoyalty)
        val badCompany = listOf(goodMoodRoyalty, goodMoodRoyalty, badMoodRoyalty)

        Servant("test").checkIfYouWillBeHanged(goodCompany).shouldBeTrue()
        Servant("test").checkIfYouWillBeHanged(badCompany).shouldBeFalse()
    }
}