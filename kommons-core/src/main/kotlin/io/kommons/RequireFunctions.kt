package io.kommons


fun <T> T?.requireNotNull(name: String) = apply {
    require(this != null) { "$name[$this] must not be null." }
}!!

fun <T> T?.requireNull(name: String) = apply {
    require(this == null) { "$name[$this] must be null." }
}

fun <T: CharSequence> T?.requireNotEmpty(name: String): T = apply {
    require(this != null && this.isNotEmpty()) { "$name[$this] must not be empty" }
}!!

fun <T: CharSequence> T?.requireEmpty(name: String) = apply {
    require(this == null || this.isEmpty()) { "$name[$this] must be empty" }
}

fun <T: CharSequence> T?.requireNotBlank(name: String): T = apply {
    require(this != null && this.isNotBlank()) { "$name[$this] must not be empty" }
}!!

fun <T: CharSequence> T?.requireBlank(name: String) = apply {
    require(this != null && this.isBlank()) { "$name[$this] must be empty" }
}

fun <T: CharSequence> T?.requireContains(other: CharSequence, name: String): T = apply {
    require(this != null && this.contains(other)) { "$name[$this] must contain $other" }
}!!

fun <T: CharSequence> T?.requireStartstWith(prefix: CharSequence, name: String, ignoreCase: Boolean = false): T = apply {
    require(this != null && this.startsWith(prefix, ignoreCase)) { "$name[$this] must be starts with $prefix" }
}!!

fun <T: CharSequence> T?.requireEndsWith(suffix: CharSequence, name: String, ignoreCase: Boolean = false): T = apply {
    require(this != null && this.endsWith(suffix, ignoreCase)) { "$name[$this] must be ends with $suffix" }
}!!

fun <T> T.requireEqual(expected: T, name: String) = apply {
    require(this == expected) { "$name[$this] must be equal to $expected" }
}

fun <T: Comparable<T>> T.requireGreaterThan(expected: T, name: String): T = apply {
    require(this > expected) { "$name[$this] must be greater than $expected." }
}

fun <T: Comparable<T>> T.requireGreaterThanOrEqualTo(expected: T, name: String): T = apply {
    require(this >= expected) { "$name[$this] must be greater than $expected." }
}

fun <T: Comparable<T>> T.requireLessThan(expected: T, name: String): T = apply {
    require(this < expected) { "$name[$this] must be greater than $expected." }
}

fun <T: Comparable<T>> T.requireLessThanOrEqualTo(expected: T, name: String): T = apply {
    require(this <= expected) { "$name[$this] must be greater than $expected." }
}

fun <T: Comparable<T>> T.requireInRange(start: T, endInclusive: T, name: String) = apply {
    require(this in start..endInclusive) { "$name[$this] must be in range ($start .. $endInclusive)" }
}

fun <T: Comparable<T>> T.requireInOpenRange(start: T, endExclusive: T, name: String): T = apply {
    require(this >= start && this < endExclusive) { "$start <= $name[$this] < $endExclusive" }
}

fun <T> T.requireZeroOrPositiveNumber(name: String): T where T: Number, T: Comparable<T> = apply {
    toDouble().requireGreaterThanOrEqualTo(0.0, name)
}

fun <T> T.requirePositiveNumber(name: String): T where T: Number, T: Comparable<T> = apply {
    toDouble().requireGreaterThan(0.0, name)
}

fun <T> T.requireZeroOrNegativeNumber(name: String): T where T: Number, T: Comparable<T> = apply {
    toDouble().requireLessThanOrEqualTo(0.0, name)
}

fun <T> T.requireNegativeNumber(name: String): T where T: Number, T: Comparable<T> = apply {
    toDouble().requireLessThan(0.0, name)
}
