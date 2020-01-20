package io.kommons.designpatterns.abstractdocument

import io.kommons.designpatterns.abstractdocument.domain.Car
import io.kommons.designpatterns.abstractdocument.domain.Part
import io.kommons.designpatterns.abstractdocument.domain.Property
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class DocumentTest {

    companion object: KLogging() {
        private const val TEST_PART_TYPE = "test-part-type"
        private const val TEST_PART_MODEL = "test-part-model"
        private const val TEST_PART_PRICE = 0L

        private const val TEST_CAR_MODEL = "test-car-model"
        private const val TEST_CAR_PRICE = 1L
    }

    @Test
    fun `construct Part`() {
        val partProps = hashMapOf<String, Any?>()
        partProps[Property.TYPE.name] = TEST_PART_TYPE
        partProps[Property.MODEL.name] = TEST_PART_MODEL
        partProps[Property.PRICE.name] = TEST_PART_PRICE

        val part = Part(partProps)

        part.getType() shouldEqual TEST_PART_TYPE
        part.getModel() shouldEqual TEST_PART_MODEL
        part.getPrice() shouldEqual TEST_PART_PRICE
    }

    @Test
    fun `construct Car`() {
        val carProps = hashMapOf<String, Any?>()
        carProps[Property.MODEL.name] = TEST_CAR_MODEL
        carProps[Property.PRICE.name] = TEST_CAR_PRICE
        carProps[Property.PARTS.name] = listOf(hashMapOf<String, Any?>(), hashMapOf())

        val car = Car(carProps)

        car.getModel() shouldEqual TEST_CAR_MODEL
        car.getPrice() shouldEqual TEST_CAR_PRICE
        car.getParts().count() shouldEqual 2
    }
}