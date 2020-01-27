package io.kommons.designpatterns.specification

enum class Color(val title: String) {
    DARK("dark"), LIGHT("light"), GREEN("green"), RED("red")
}

enum class Movement(val title: String) {
    WALKING("walking"), SWIMMING("swimming"), FLYING("flying")
}

enum class Size(val title: String) {
    SMALL("small"), NORMAL("small"), LARGE("large")
}

data class Mass(val value: Double): Comparable<Mass> {
    private val title: String = "${value}kg"

    override fun compareTo(other: Mass): Int = when {
        value > other.value -> 1
        value < other.value -> -1
        else                -> 0
    }

    override fun toString(): String = title
}