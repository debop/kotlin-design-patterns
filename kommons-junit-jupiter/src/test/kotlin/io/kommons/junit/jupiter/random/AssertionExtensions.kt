package io.kommons.junit.jupiter.random

import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldNotBeNull
import org.amshove.kluent.shouldNotEqual
import org.amshove.kluent.shouldNotEqualTo

fun DomainObject?.shouldFullyPopulated() {
    this.shouldNotBeNull()
    assertThatDomainObjectIsFullyPopulated(this)
}

fun DomainObject?.shouldPartiallyPopulated() {
    this.shouldNotBeNull()
    assertThatDomainObjectIsPartiallyPopulated(this)
}

fun assertThatDomainObjectIsFullyPopulated(domainObject: DomainObject?) {

    domainObject.shouldNotBeNull()

    domainObject.id.shouldNotBeNull() shouldNotEqualTo 0
    domainObject.name.shouldNotBeNull() shouldNotEqual ""

    domainObject.nestedDomainObject?.shouldNotBeNull()
    domainObject.nestedDomainObject?.address.shouldNotBeNull()
    domainObject.nestedDomainObject?.category.shouldNotBeNull()

    domainObject.wotsits.shouldNotBeNull()
    domainObject.wotsits.shouldNotBeEmpty()

    domainObject.value.shouldNotBeNull() shouldNotEqualTo 0L
    domainObject.price.shouldNotBeNull() shouldNotEqualTo 0.0

    domainObject.objectLists.shouldNotBeNull()
    domainObject.objectLists.shouldNotBeEmpty()
}

fun assertThatDomainObjectIsPartiallyPopulated(domainObject: DomainObject?) {
    domainObject.shouldNotBeNull()

    domainObject.id.shouldNotBeNull() shouldEqualTo 0
    domainObject.name.shouldNotBeNull() shouldNotEqual ""

    domainObject.nestedDomainObject?.shouldNotBeNull()
    domainObject.nestedDomainObject?.address.shouldBeNull()
    domainObject.nestedDomainObject?.category.shouldNotBeNull()

    domainObject.wotsits.shouldBeNull()

    domainObject.value.shouldNotBeNull() shouldNotEqualTo 0L
    domainObject.price.shouldNotBeNull() shouldNotEqualTo 0.0
}

fun getDefaultSizeOfRandom(): Int {
    val clazz = RandomValue::class.java
    val method = clazz.getDeclaredMethod("size")
    return method.defaultValue as Int
}