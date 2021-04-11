package io.kommons.designpatterns.eventsourcing

import io.kommons.designpatterns.eventsourcing.App.Companion.ACCOUNT_OF_DAENERYS
import io.kommons.designpatterns.eventsourcing.App.Companion.ACCOUNT_OF_JON
import io.kommons.designpatterns.eventsourcing.event.AccountCreateEvent
import io.kommons.designpatterns.eventsourcing.event.MoneyDepositEvent
import io.kommons.designpatterns.eventsourcing.event.MoneyTransferEvent
import io.kommons.designpatterns.eventsourcing.processor.DomainEventProcessor
import io.kommons.designpatterns.eventsourcing.state.AccountAggregate
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.System.currentTimeMillis
import java.math.BigDecimal

class IntegrationTest {

    private lateinit var eventProcessor: DomainEventProcessor

    @BeforeEach
    fun setup() {
        eventProcessor = DomainEventProcessor()
    }

    @Test
    fun `state recovery`() {
        eventProcessor.reset()

        with(eventProcessor) {
            process(AccountCreateEvent(0, currentTimeMillis(), ACCOUNT_OF_DAENERYS, "Daenerys Targaryen"))

            process(AccountCreateEvent(1, currentTimeMillis(), ACCOUNT_OF_JON, "Jon Snow"))

            process(MoneyDepositEvent(2, currentTimeMillis(), ACCOUNT_OF_DAENERYS, BigDecimal("100000")))

            process(MoneyDepositEvent(3, currentTimeMillis(), ACCOUNT_OF_JON, BigDecimal("100")))

            process(MoneyTransferEvent(4, currentTimeMillis(), BigDecimal("10000"), ACCOUNT_OF_DAENERYS, ACCOUNT_OF_JON))
        }

        val accountOfDaenerysBeforeShutdown = AccountAggregate.get(ACCOUNT_OF_DAENERYS)!!
        val accountOfJonBeforeShutdown = AccountAggregate.get(ACCOUNT_OF_JON)!!

        AccountAggregate.resetState()

        eventProcessor = DomainEventProcessor()
        eventProcessor.recover()

        val accountOfDaenerysAfterShutdown = AccountAggregate.get(ACCOUNT_OF_DAENERYS)!!
        val accountOfJonAfterShutdown = AccountAggregate.get(ACCOUNT_OF_JON)!!

        accountOfDaenerysAfterShutdown.money shouldBeEqualTo accountOfDaenerysBeforeShutdown.money
        accountOfJonAfterShutdown.money shouldBeEqualTo accountOfJonBeforeShutdown.money
    }
}