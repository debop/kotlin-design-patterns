package io.kommons.designpatterns.roleobject

class InvestorRole: CustomerRole() {

    var name: String = ""
    var amountToInvest: Long = 0L

    fun invest(): String =
        "Investor $name has invested $amountToInvest dollars"
}