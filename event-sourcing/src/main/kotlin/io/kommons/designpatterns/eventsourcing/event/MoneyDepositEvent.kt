package io.kommons.designpatterns.eventsourcing.event

import io.kommons.designpatterns.eventsourcing.state.AccountAggregate
import java.math.BigDecimal

/**
 * MoneyDepositEvent
 *
 * @author debop
 * @since 19. 9. 18.
 */
class MoneyDepositEvent(sequenceId: Long,
                        createdTime: Long,
                        val accountNo: Int,
                        val money: BigDecimal): AbstractDomainEvent(sequenceId, createdTime, "MoneyDepositEvent") {

    override fun process() {
        val account = AccountAggregate.get(accountNo) ?: error("Account not found. accountNo=$accountNo")
        account.handleEvent(this)
    }
}