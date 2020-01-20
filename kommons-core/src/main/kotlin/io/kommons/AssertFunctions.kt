package io.kommons

fun <T: Any> T?.assertNotNull(name: String): T = apply {
    assert(this != null) { "$name[$this] must not be null." }
}!!

fun <T: Any> T?.assertNull(name: String): T? = apply {
    assert(this == null) { "$name[$this] must be null." }
}

fun <T: CharSequence> T?.assertNotEmpty(name: String): T = apply {
    this.assertNotNull(name)
    assert(this!!.isNotEmpty()) { "$name[$this] must not be null or empty." }
}!!

fun <T: CharSequence> T?.assertNullOrEmtpy(name: String): T? = apply {
    assert(this.isNullOrEmpty()) { "$name[$this] must be null or empty." }
}

fun <T: CharSequence> T?.assertNotBlank(name: String): T = apply {
    assert(this != null && this.isNotBlank()) { "$name[$this] must not be null or blank." }
}!!

fun <T: CharSequence> T?.assertNullOrBlank(name: String): T? = apply {
    assert(this.isNullOrBlank()) { "$name[$this] must not be null or blank." }
}!!

fun <T: CharSequence> T?.assertContains(other: CharSequence, name: String): T? = apply {
    assert(this != null && this.contains(other)) { "$name[$this] must contain $other" }
}

fun <T: CharSequence> T?.assertStartsWith(prefix: CharSequence, name: String, ignoreCase: Boolean = false): T = apply {
    assert(this != null && this.startsWith(prefix, ignoreCase)) { "$name[$this] must be starts with $prefix" }
}!!

fun <T: CharSequence> T?.assertEndsWith(prefix: CharSequence, name: String, ignoreCase: Boolean = false): T = apply {
    assert(this != null && this.endsWith(prefix, ignoreCase)) { "$name[$this] must be ends with $prefix" }
}!!

fun <T> T.assertEquals(expected: T, name: String): T = apply {
    assert(this == expected) { "$name[$this] must be equal to $expected" }
}

fun <T: Comparable<T>> T.assertGreaterThan(expected: T, name: String): T = apply {
    assert(this > expected) { "$name[$this] must be greater than $expected." }
}

fun <T: Comparable<T>> T.assertGreaterThanOrEqualTo(expected: T, name: String): T = apply {
    assert(this >= expected) { "$name[$this] must be greater than $expected." }
}

fun <T: Comparable<T>> T.assertLessThan(expected: T, name: String): T = apply {
    assert(this < expected) { "$name[$this] must be greater than $expected." }
}

fun <T: Comparable<T>> T.assertLessThanOrEqualTo(expected: T, name: String): T = apply {
    assert(this <= expected) { "$name[$this] must be greater than $expected." }
}

fun <T: Comparable<T>> T.assertInRange(start: T, endInclusive: T, name: String) = apply {
    assert(this in start..endInclusive) { "$name[$this] must be in range ($start .. $endInclusive)" }
}

fun <T: Comparable<T>> T.assertInOpenRange(start: T, endExclusive: T, name: String): T = apply {
    assert(this >= start && this < endExclusive) { "$start <= $name[$this] < $endExclusive" }
}

fun <T> T.assertZeroOrPositiveNumber(name: String): T where T: Number, T: Comparable<T> = apply {
    toDouble().assertGreaterThanOrEqualTo(0.0, name)
}

fun <T> T.assertPositiveNumber(name: String): T where T: Number, T: Comparable<T> = apply {
    toDouble().assertGreaterThan(0.0, name)
}

fun <T> T.assertZeroOrNegativeNumber(name: String): T where T: Number, T: Comparable<T> = apply {
    toDouble().assertLessThanOrEqualTo(0.0, name)
}

fun <T> T.assertNegativeNumber(name: String): T where T: Number, T: Comparable<T> = apply {
    toDouble().assertLessThan(0.0, name)
}