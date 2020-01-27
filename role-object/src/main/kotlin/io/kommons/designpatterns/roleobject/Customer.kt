package io.kommons.designpatterns.roleobject

interface Customer {

    fun addRole(role: Role): Boolean

    fun hasRole(role: Role): Boolean

    fun removeRole(role: Role): Boolean

    fun <T: Customer> getRole(role: Role, expectedRole: Class<T>): T?

    companion object {
        fun newCustomer(): CustomerCore = CustomerCore()

        fun newCustomer(vararg roles: Role): CustomerCore =
            newCustomer().apply {
                roles.forEach { addRole(it) }
            }
    }
}