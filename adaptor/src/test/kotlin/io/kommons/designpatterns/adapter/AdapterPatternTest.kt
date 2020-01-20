package io.kommons.designpatterns.adapter

import io.kommons.logging.KLogging
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AdapterPatternTest {

    companion object: KLogging() {
        private const val FISHING_BEAN = "fisher"
        private const val ROWING_BEAN = "captain"
    }

    private val beans = mutableMapOf<String, Any>()


    @BeforeEach
    fun setup() {
        beans.clear()

        val fishingBoatAdapter = spyk<FishingBoatAdapter>()
        beans.put(FISHING_BEAN, fishingBoatAdapter)

        val captain = Captain(beans[FISHING_BEAN] as FishingBoatAdapter)
        beans[ROWING_BEAN] = captain
    }

    @Test
    fun `test adapter`() {
        val captain = beans[ROWING_BEAN] as Captain

        captain.row()

        val adapter = beans[FISHING_BEAN] as RowingBoat

        verify(exactly = 1) {
            adapter.row()
        }
    }
}