package io.kommons.junit.jupiter.random

class DomainObject {

    var id: Int = 0
    var name: String? = null
    var value: Long = 0L
    var price: Double = 0.0
    val nestedDomainObject: NestedDomainObject? = null
    val wotsits: List<String>? = null
    val objectLists: List<NestedDomainObject> = emptyList()
}