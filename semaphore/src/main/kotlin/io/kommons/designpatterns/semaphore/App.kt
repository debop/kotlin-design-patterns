package io.kommons.designpatterns.semaphore

class App

fun main() {
    val shop = FruitShop()
    val customerNames = listOf("Peter", "Paul", "Mary", "John", "Ringo", "George")

    val customers = customerNames.map { Customer(it, shop) }
    customers.forEach { it.start() }
    customers.forEach { it.join() }
}