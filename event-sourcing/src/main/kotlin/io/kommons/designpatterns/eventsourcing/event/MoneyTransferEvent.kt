package io.kommons.designpatterns.eventsourcing.event

import io.kommons.designpatterns.eventsourcing.state.AccountAggregate
import java.math.BigDecimal

/**
 * MoneyTransferEvent
 *
 * @author debop
 * @since 19. 9. 18.
 */
class MoneyTransferEvent(sequenceId: Long,
                         createdTime: Long,
                         val money: BigDecimal,
                         val accountNoFrom: Int,
                         val accountNoTo: Int): AbstractDomainEvent(sequenceId, createdTime, "MoneyTransferEvent") {

    override fun process() {
        val accountFrom = AccountAggregate.get(accountNoFrom) ?: error("Account not found. accountNoFrom=$accountNoFrom")
        val accountTo = AccountAggregate.get(accountNoTo) ?: error("Account not found. accountNoTo=$accountNoTo")

        accountFrom.handleTransferFromEvent(this)
        accountTo.handleTransferToEvent(this)
    }
}