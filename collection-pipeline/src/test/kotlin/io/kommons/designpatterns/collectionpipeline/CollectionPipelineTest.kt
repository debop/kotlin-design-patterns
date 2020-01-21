package io.kommons.designpatterns.collectionpipeline

import io.kommons.designpatterns.collectionpipeline.Category.CONVERTIBLE
import io.kommons.designpatterns.collectionpipeline.Category.JEEP
import io.kommons.designpatterns.collectionpipeline.Category.SEDAN
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldContainSame
import org.junit.jupiter.api.Test

class CollectionPipelineTest {

    companion object: KLogging()

    private val cars = CarFactory.createCars()
    private val person = Person(cars)

    @Test
    fun `run kotlin style`() {

        val models = KotlinFunctionalProgramming.getModelsAfter2000(cars)
        models shouldContainSame listOf("Avenger", "Wrangler", "Focus", "Cascada")

        val groupingByCategory = KotlinFunctionalProgramming.getGroupingOfCarsByCategory(cars)
        groupingByCategory.keys shouldContainSame listOf(JEEP, SEDAN, CONVERTIBLE)

        val sedansOwned = KotlinFunctionalProgramming.getSedanCarsOwnedSortedByData(listOf(person))
        sedansOwned shouldContainSame listOf(Car("Dodge", "Avenger", 2010, SEDAN),
                                             Car("Ford", "Focus", 2012, SEDAN))
    }

    @Test
    fun `run java stream style`() {

        val models = JavaFunctionalProgramming.getModelsAfter2000(cars)
        models shouldContainSame listOf("Avenger", "Wrangler", "Focus", "Cascada")

        val groupingByCategory = JavaFunctionalProgramming.getGroupingOfCarsByCategory(cars)
        groupingByCategory.keys shouldContainSame listOf(JEEP, SEDAN, CONVERTIBLE)

        val sedansOwned = JavaFunctionalProgramming.getSedanCarsOwnedSortedByData(listOf(person))
        sedansOwned shouldContainSame listOf(Car("Dodge", "Avenger", 2010, SEDAN),
                                             Car("Ford", "Focus", 2012, SEDAN))
    }
}