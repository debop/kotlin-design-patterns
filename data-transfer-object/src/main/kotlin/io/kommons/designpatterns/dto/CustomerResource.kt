package io.kommons.designpatterns.dto

/**
 * The resource class which serves customer information.
 * This class act as server in the demo. Which has all customer details.
 */
class CustomerResource(private val customers: MutableList<CustomerDto> = mutableListOf()) {

    fun getAllCustomers(): List<CustomerDto> = ArrayList(customers)

    fun save(customer: CustomerDto) {
        customers.add(customer)
    }

    fun delete(customerId: String) {
        customers.removeIf { customer -> customer.id == customerId }
    }

    fun clear() {
        customers.clear()
    }
}