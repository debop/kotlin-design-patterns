package io.kommons.designpatterns.roleobject

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class BorrowerRoleTest {

    @Test
    fun `run borrow`() {
        val borrowerRole = BorrowerRole().apply { name = "test" }
        borrowerRole.borrow() shouldEqual "Borrower test wants to get some money."
    }
}