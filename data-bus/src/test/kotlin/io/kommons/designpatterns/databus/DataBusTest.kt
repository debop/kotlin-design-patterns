package io.kommons.designpatterns.databus

import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DataBusTest {

    private val member: Member = mockk(relaxUnitFun = true)
    private val event: DataType = mockk(relaxUnitFun = true)

    @BeforeEach
    fun setup() {
        clearMocks(member, event)
    }

    @Test
    fun `published event is received by subscribe member`() {
        val dataBus = DataBus.getInstance()

        dataBus.subscribe(member)

        dataBus.publish(event)

        verify { member.accept(event) }
    }

    @Test
    fun `published event is not received by member after unsubscribing`() {
        val dataBus = DataBus.getInstance()
        dataBus.subscribe(member)
        dataBus.unsubscribe(member)

        dataBus.publish(event)

        verify(exactly = 0) { member.accept(event) }
    }
}