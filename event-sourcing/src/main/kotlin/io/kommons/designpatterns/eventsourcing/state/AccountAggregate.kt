package io.kommons.designpatterns.eventsourcing.state

import io.kommons.designpatterns.eventsourcing.domain.Account
import io.kommons.logging.KLogging

/**
 * AccountAggregate
 *
 * @author debop
 * @since 19. 9. 18.
 */
class AccountAggregate {

    companion object: KLogging() {
        private val accounts = mutableMapOf<Int, Account>()

        fun put(account: Account) {
            accounts[account.accountNo] = account
        }

        fun get(accountNo: Int): Account? {
            return accounts[accountNo]?.copy()
        }

        fun resetState() {
            accounts.clear()
        }
    }
}