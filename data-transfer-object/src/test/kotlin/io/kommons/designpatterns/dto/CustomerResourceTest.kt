package io.kommons.designpatterns.dto

import io.kommons.junit.jupiter.random.RandomValue
import io.kommons.junit.jupiter.random.RandomizedTest
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest

/**
 * CustomerResourceTest
 *
 * @author debop
 */
@RandomizedTest
class CustomerResourceTest {

    private val customerResource = CustomerResource()

    @BeforeEach
    fun setup() {
        customerResource.clear()
    }

    @RepeatedTest(10)
    fun `should get all customers`(@RandomValue customerDto: CustomerDto) {

        customerResource.save(customerDto)

        val allCustomers = customerResource.getAllCustomers()
        allCustomers.size shouldEqualTo 1
        allCustomers.first() shouldEqual customerDto
    }

    @RepeatedTest(10)
    fun `should save customers`(@RandomValue(type = CustomerDto::class) customers: List<CustomerDto>) {
        customers.forEach {
            customerResource.save(it)
        }

        val allCustomers = customerResource.getAllCustomers()

        allCustomers.size shouldEqualTo customers.size
        allCustomers shouldContainSame customers
    }

    @RepeatedTest(10)
    fun `should delete customers`(@RandomValue(type = CustomerDto::class) customers: List<CustomerDto>) {
        customers.forEach {
            customerResource.save(it)
        }

        customers.forEach {
            customerResource.delete(it.id)
        }

        val allCustomers = customerResource.getAllCustomers()
        allCustomers.shouldBeEmpty()
    }
}