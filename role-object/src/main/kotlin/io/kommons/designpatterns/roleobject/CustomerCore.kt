package io.kommons.designpatterns.roleobject

open class CustomerCore: Customer {

    private val roles = HashMap<Role, CustomerRole>()

    override fun addRole(role: Role): Boolean {
        return role.instance<CustomerRole>()?.let { roles[role] = it; true } ?: false
    }

    override fun hasRole(role: Role): Boolean {
        return roles.containsKey(role)
    }

    override fun removeRole(role: Role): Boolean {
        return roles.remove(role) != null
    }

    override fun <T: Customer> getRole(role: Role, expectedRole: Class<T>): T? =
        roles[role]?.takeIf { expectedRole.isInstance(it) }?.let { expectedRole.cast(it) }

    inline fun <reified T: Customer> getRole(role: Role): T? =
        getRole(role, T::class.java)

    override fun toString(): String {
        val roleNames = roles.keys.map { it.name }
        return "Customer {roles=$roleNames}"
    }
}