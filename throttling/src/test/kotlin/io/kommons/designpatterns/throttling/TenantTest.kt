package io.kommons.designpatterns.throttling

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TenantTest {

    @Test
    fun `create tenant with invalid parameter`() {
        assertThrows<IllegalArgumentException> {
            Tenant("FailTenant", -1, CallsCount())
        }
    }

    @Test
    fun `create tenant with valid parameter`() {
        Tenant("SuccessTenant", 100, CallsCount())
    }
}