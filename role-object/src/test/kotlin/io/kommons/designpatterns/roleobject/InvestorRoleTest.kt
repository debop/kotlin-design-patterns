package io.kommons.designpatterns.roleobject

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class InvestorRoleTest {

    @Test
    fun `test investor role`() {
        val investorRole = InvestorRole().apply {
            name = "test"
            amountToInvest = 10
        }

        investorRole.invest() shouldEqual "Investor test has invested 10 dollars"
    }
}