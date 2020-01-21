package io.kommons.designpatterns.eventsourcing.event

import io.kommons.designpatterns.eventsourcing.domain.Account
import io.kommons.designpatterns.eventsourcing.state.AccountAggregate

/**
 * AccountCreateEvent
 *
 * @author debop
 * @since 19. 9. 18.
 */
class AccountCreateEvent(sequenceId: Long,
                         createdTime: Long,
                         val accountNo: Int,
                         val owner: String): AbstractDomainEvent(sequenceId, createdTime, "AccountCreateEvent") {

    override fun process() {
        var account = AccountAggregate.get(accountNo)

        if (account != null) {
            error("Account already exists. accountNo=$accountNo, owner=$owner")
        }
        account = Account(accountNo, owner)
        account.handleEvent(this)
    }
}