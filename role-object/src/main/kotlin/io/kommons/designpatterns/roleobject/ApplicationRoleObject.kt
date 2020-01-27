package io.kommons.designpatterns.roleobject

import io.kommons.designpatterns.roleobject.Role.Borrower
import io.kommons.designpatterns.roleobject.Role.Investor
import io.kommons.logging.KotlinLogging
import io.kommons.logging.info

class ApplicationRoleObject

private val log = KotlinLogging.logger { }

fun main() {
    val customer = Customer.newCustomer(Borrower, Investor)

    log.info { " the new customer created: $customer" }

    val hasBorrowerRole = customer.hasRole(Borrower)
    log.info { " customer has a borrowed role - $hasBorrowerRole" }

    val hasInvestorRole = customer.hasRole(Investor)
    log.info { " customer has a investor role - $hasInvestorRole" }

    customer.getRole(Investor, InvestorRole::class.java)
        ?.let {
            it.amountToInvest = 1000
            it.name = "Billy"
        }

    customer.getRole(Borrower, BorrowerRole::class.java)
        ?.let {
            it.name = "Johny"
        }

    customer.getRole(Investor, InvestorRole::class.java)?.let { log.info { it.invest() } }
    customer.getRole(Borrower, BorrowerRole::class.java)?.let { log.info { it.borrow() } }

    customer.getRole<InvestorRole>(Investor)?.let { log.info { it.invest() } }
    customer.getRole<BorrowerRole>(Borrower)?.let { log.info { it.borrow() } }
}