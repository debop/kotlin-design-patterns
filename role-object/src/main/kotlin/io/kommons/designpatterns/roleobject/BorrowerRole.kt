package io.kommons.designpatterns.roleobject

class BorrowerRole: CustomerRole() {

    var name: String = ""

    fun borrow(): String {
        return "Borrower $name wants to get some money."
    }
}