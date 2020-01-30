package io.kommons.designpatterns.retry

import java.util.ArrayDeque

class FindCustomer(private val customerId: String,
                   vararg _errors: BusinessException): BusinessOperation<String> {

    private val errors = ArrayDeque(_errors.toList())

    override fun perform(): String {
        if (errors.isNotEmpty()) {
            throw this.errors.pop()
        }
        return this.customerId
    }
}