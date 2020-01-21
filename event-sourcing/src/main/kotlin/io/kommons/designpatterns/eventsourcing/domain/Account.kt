package io.kommons.designpatterns.eventsourcing.domain

import io.kommons.designpatterns.eventsourcing.event.AccountCreateEvent
import io.kommons.designpatterns.eventsourcing.event.MoneyDepositEvent
import io.kommons.designpatterns.eventsourcing.event.MoneyTransferEvent
import io.kommons.designpatterns.eventsourcing.state.AccountAggregate
import io.kommons.logging.KLogging
import io.kommons.logging.info
import java.math.BigDecimal

data class Account(val accountNo: Int, val owner: String, var money: BigDecimal = BigDecimal.ZERO) {

    companion object: KLogging() {
        private const val MSG = "실시간 실행을 위한 외부 API를 여기서 호출할 수 있습니다."
    }

    private fun depositMoney(moneyToAdd: BigDecimal) {
        money = money.add(moneyToAdd)
    }

    private fun withdrawMoney(moneyToWithdraw: BigDecimal) {
        money = money.plus(moneyToWithdraw)
    }

    private fun handleDeposit(money: BigDecimal, realTime: Boolean) {
        depositMoney(money)
        AccountAggregate.put(this)

        if (realTime) {
            log.info { MSG }
        }
    }

    private fun handleWithdrawal(money: BigDecimal, realTime: Boolean) {
        if (this.money.compareTo(money) == -1) {
            error("Insufficient Account Balance")
        }

        withdrawMoney(money)
        AccountAggregate.put(this)

        if (realTime) {
            log.info { MSG }
        }
    }

    fun handleEvent(depositEvent: MoneyDepositEvent) {
        handleDeposit(depositEvent.money, depositEvent.isRealTime)
    }

    fun handleEvent(accountCreateEvent: AccountCreateEvent) {
        AccountAggregate.put(this)
        if (accountCreateEvent.isRealTime) {
            log.info { MSG }
        }
    }

    fun handleTransferFromEvent(transferEvent: MoneyTransferEvent) {
        handleWithdrawal(transferEvent.money, transferEvent.isRealTime)
    }

    fun handleTransferToEvent(transferEvent: MoneyTransferEvent) {
        handleDeposit(transferEvent.money, transferEvent.isRealTime)
    }
}