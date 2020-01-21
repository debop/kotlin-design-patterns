package io.kommons.designpatterns.event.aggregator

/**
 * Weekday enumeration
 *
 * @author debop
 */
enum class Weekday(val description: String) {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    override fun toString(): String = description

}