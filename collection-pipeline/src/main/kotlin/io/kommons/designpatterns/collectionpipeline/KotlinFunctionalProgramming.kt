package io.kommons.designpatterns.collectionpipeline

/**
 * FunctionalProgramming
 *
 * @author debop
 */
object KotlinFunctionalProgramming {

    fun getModelsAfter2000(cars: List<Car>): List<String> =
        cars
            .filter { it.yearOfMake > 2000 }
            .sortedBy { it.yearOfMake }
            .map { it.model }

    fun getGroupingOfCarsByCategory(cars: List<Car>): Map<Category, List<Car>> =
        cars.groupBy { it.category }

    fun getSedanCarsOwnedSortedByData(persons: List<Person>): List<Car> =
        persons
            .flatMap { it.cars }
            .filter { it.category == Category.SEDAN }
            .sortedBy { it.yearOfMake }

}