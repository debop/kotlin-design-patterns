package io.kommons.junit.jupiter.random

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldNotBeNull
import org.amshove.kluent.shouldNotBeNullOrBlank
import org.amshove.kluent.shouldNotContain
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import java.util.stream.Stream

@RandomizedTest
@TestInstance(Lifecycle.PER_CLASS)
class RandomExtensionParameterTest {

    companion object: KLogging() {
        const val TEST_COUNT = 20
    }

    private val anyStrings = HashSet<String>()
    private val anyNumbers = HashSet<Int>()

    @RepeatedTest(TEST_COUNT)
    fun `can inject a random string`(@RandomValue anyString: String?) {
        anyString.shouldNotBeNullOrBlank()
    }

    @RepeatedTest(TEST_COUNT)
    fun `can inject a random string and numbers`(@RandomValue anyString: String?,
                                                 @RandomValue anyInt: Int?,
                                                 @RandomValue anyDouble: Double?) {
        anyString.shouldNotBeNullOrBlank()
        anyInt.shouldNotBeNull()
        anyDouble.shouldNotBeNull()
    }

    @RepeatedTest(TEST_COUNT)
    fun `can inject a fully populated random object`(@RandomValue domainObject: DomainObject) {
        domainObject.shouldFullyPopulated()
    }

    @RepeatedTest(TEST_COUNT)
    fun `can inject a partially populated random object`(
        @RandomValue(excludes = ["wotsits", "id", "nestedDomainObject.address"])
        domainObject: DomainObject
    ) {
        domainObject.shouldPartiallyPopulated()
    }

    @RepeatedTest(TEST_COUNT)
    fun `can inject a random list of default size`(@RandomValue(type = String::class) anyList: List<String>) {
        anyList.shouldNotBeNull()
        anyList.shouldNotBeEmpty()
        anyList.size shouldEqualTo getDefaultSizeOfRandom()
    }

    @RepeatedTest(TEST_COUNT)
    fun `can inject a ramdom set`(@RandomValue(type = String::class) anySet: Set<String>) {
        anySet.shouldNotBeNull()
        anySet.shouldNotBeEmpty()
        anySet.size shouldEqualTo getDefaultSizeOfRandom()
    }

    @RepeatedTest(TEST_COUNT)
    fun `can inject a random stream`(@RandomValue(type = String::class) anyStream: Stream<String>) {
        anyStream.shouldNotBeNull()
        anyStream.count() shouldEqualTo getDefaultSizeOfRandom().toLong()
    }

    @RepeatedTest(TEST_COUNT)
    fun `can inject a ramdom collection`(@RandomValue(type = String::class) anyCollection: Collection<String>) {
        anyCollection.shouldNotBeNull()
        anyCollection.shouldNotBeEmpty()
        anyCollection.size shouldEqualTo getDefaultSizeOfRandom()
    }

    @RepeatedTest(TEST_COUNT)
    fun `can inject random fully populated domain objects`(
        @RandomValue(size = 2, type = DomainObject::class) anyFullyPopulatedDomainObjects: List<DomainObject>) {
        anyFullyPopulatedDomainObjects.shouldNotBeNull()
        anyFullyPopulatedDomainObjects.shouldNotBeEmpty()
        anyFullyPopulatedDomainObjects.size shouldEqualTo 2
        anyFullyPopulatedDomainObjects.forEach {
            it.shouldFullyPopulated()
        }
    }

    @RepeatedTest(TEST_COUNT)
    fun `can inject random partially populated domain objects`(
        @RandomValue(size = 2, type = DomainObject::class, excludes = ["wotsits", "id", "nestedDomainObject.address"])
        anyPartiallyPopulatedDomainObjects: List<DomainObject>) {

        anyPartiallyPopulatedDomainObjects.shouldNotBeNull()
        anyPartiallyPopulatedDomainObjects.shouldNotBeEmpty()
        anyPartiallyPopulatedDomainObjects.size shouldEqualTo 2
        anyPartiallyPopulatedDomainObjects.forEach {
            it.shouldPartiallyPopulated()
        }
    }

    @RepeatedTest(TEST_COUNT)
    fun `will inject a new random values each time`(@RandomValue anyString: String,
                                                    @RandomValue anyNumber: Int) {

        anyStrings.shouldNotContain(anyString)
        anyStrings.add(anyString)

        anyNumbers.shouldNotContain(anyNumber)
        anyNumbers.add(anyNumber)
    }
}