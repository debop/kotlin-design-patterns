package io.kommons.designpatterns.collectionpipeline

import java.util.stream.Collectors

/**
 * JavaFunctionalProgramming
 *
 * @author debop
 */
object JavaFunctionalProgramming {

    fun getModelsAfter2000(cars: List<Car>): List<String> =
        cars.stream()
            .filter { it.yearOfMake > 2000 }
            .sorted(Comparator.comparing<Car, Int> { it.yearOfMake })
            .map { it.model }
            .collect(Collectors.toList())

    fun getGroupingOfCarsByCategory(cars: List<Car>): Map<Category, List<Car>> =
        cars.stream().collect(Collectors.groupingBy { car -> car.category })

    fun getSedanCarsOwnedSortedByData(persons: List<Person>): List<Car> =
        persons.stream()
            .flatMap { it.cars.stream() }
            .filter { it.category == Category.SEDAN }
            .sorted(Comparator.comparing<Car, Int> { it.yearOfMake })
            .collect(Collectors.toList())

}