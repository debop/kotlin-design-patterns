package io.kommons.designpatterns.collectionpipeline

/**
 * CarFactory
 *
 * @author debop
 */
object CarFactory {

    fun createCars(): List<Car> = listOf(
        Car("Jeep", "Wrangler", 2011, Category.JEEP),
        Car("Jeep", "Comanche", 1990, Category.JEEP),
        Car("Dodge", "Avenger", 2010, Category.SEDAN),
        Car("Buick", "Cascada", 2016, Category.CONVERTIBLE),
        Car("Ford", "Focus", 2012, Category.SEDAN),
        Car("Chevrolet", "Geo Metro", 1992, Category.CONVERTIBLE)
    )
}