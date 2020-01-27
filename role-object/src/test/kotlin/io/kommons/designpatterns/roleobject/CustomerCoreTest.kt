package io.kommons.designpatterns.roleobject

import io.kommons.designpatterns.roleobject.Role.Borrower
import io.kommons.designpatterns.roleobject.Role.Investor
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test

class CustomerCoreTest {

    companion object: KLogging()

    @Test
    fun `check add role`() {
        val core = CustomerCore()
        core.addRole(Role.Borrower).shouldBeTrue()
    }

    @Test
    fun `check has role`() {
        val core = CustomerCore().apply { addRole(Role.Borrower) }

        core.hasRole(Role.Borrower).shouldBeTrue()
        core.hasRole(Role.Investor).shouldBeFalse()
    }

    @Test
    fun `check remove role`() {
        val core = CustomerCore().apply { addRole(Borrower) }


        val bRole = core.getRole(Borrower, BorrowerRole::class.java)
        bRole.shouldNotBeNull()

        core.removeRole(Borrower).shouldBeTrue()

        val notexists = core.getRole(Borrower, BorrowerRole::class.java)
        notexists.shouldBeNull()
    }

    @Test
    fun `check get role`() {
        val core = CustomerCore().apply { addRole(Borrower) }

        core.getRole<BorrowerRole>(Borrower).shouldNotBeNull()

        core.getRole<InvestorRole>(Borrower).shouldBeNull()
        core.getRole<InvestorRole>(Investor).shouldBeNull()
    }

    @Test
    fun `check toString`() {
        var core = CustomerCore().apply { addRole(Borrower) }
        core.toString() shouldEqual "Customer {roles=[Borrower]}"

        core = CustomerCore().apply { addRole(Investor) }
        core.toString() shouldEqual "Customer {roles=[Investor]}"

        core = CustomerCore()
        core.toString() shouldEqual "Customer {roles=[]}"
    }
}