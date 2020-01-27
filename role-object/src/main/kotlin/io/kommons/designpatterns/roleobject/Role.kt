package io.kommons.designpatterns.roleobject

enum class Role(val typeCast: Class<out CustomerRole>) {

    Borrower(BorrowerRole::class.java),
    Investor(InvestorRole::class.java);

    @Suppress("UNCHECKED_CAST")
    fun <T: CustomerRole> instance(): T? =
        runCatching { typeCast.newInstance() as? T }.getOrNull()
}