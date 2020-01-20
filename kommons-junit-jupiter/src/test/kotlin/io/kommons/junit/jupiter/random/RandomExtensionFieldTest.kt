package io.kommons.junit.jupiter.random

import io.kommons.logging.KLogging
import io.kommons.logging.trace
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldNotBeNull
import org.amshove.kluent.shouldNotBeNullOrEmpty
import org.amshove.kluent.shouldNotContain
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.stream.Stream

@RandomizedTest
@TestInstance(Lifecycle.PER_METHOD)
class RandomExtensionFieldTest {

    companion object: KLogging() {
        const val TEST_COUNT = 20
    }

    val anyStrings = ConcurrentLinkedQueue<String>()

    @RandomValue
    private lateinit var anyString: String

    @Test
    fun `inject random string`() {
        log.trace { "anyString=$anyString" }
        anyString.shouldNotBeNull()
    }

    @RandomValue
    private lateinit var fullyPopulatedDomainObject: DomainObject

    @Test
    fun `inject fully populated random object`() {
        fullyPopulatedDomainObject.shouldFullyPopulated()
    }

    @RandomValue(excludes = ["wotsits", "id", "nestedDomainObject.address"])
    private lateinit var partiallyPopulatedDomainObject: DomainObject

    @Test
    fun `inject partial populated random object`() {
        partiallyPopulatedDomainObject.shouldPartiallyPopulated()
    }

    @RandomValue(type = String::class)
    private lateinit var anyList: List<String>

    @Test
    fun `can inject a random list of default size`() {
        anyList.shouldNotBeNull()
        anyList.shouldNotBeEmpty()
        anyList.size shouldEqualTo getDefaultSizeOfRandom()
    }

    @RandomValue(size = 5, type = String::class)
    private lateinit var anyListOfSpecificSize: List<String>

    @Test
    fun `can inject a random list of specific size`() {
        anyListOfSpecificSize.shouldNotBeNull()
        anyListOfSpecificSize.shouldNotBeEmpty()
        anyListOfSpecificSize.size shouldEqualTo 5
    }


    @RandomValue(type = String::class)
    private lateinit var anySet: Set<String>

    @Test
    fun `can inject a random set`() {
        anySet.shouldNotBeNull()
        anySet.shouldNotBeEmpty()
        anySet.size shouldEqualTo getDefaultSizeOfRandom()
    }

    @RandomValue(type = String::class)
    private lateinit var anyStream: Stream<String>

    @Test
    fun `can inject a random stream`() {
        anyStream.shouldNotBeNull()
        anyStream.count() shouldEqualTo getDefaultSizeOfRandom().toLong()
    }

    @RandomValue(type = String::class)
    private lateinit var anyCollection: Collection<String>

    @Test
    fun `can inject a random collection`() {
        anyCollection.shouldNotBeNull()
        anyCollection.shouldNotBeEmpty()
        anyCollection.size shouldEqualTo getDefaultSizeOfRandom()
    }


    @RandomValue(size = 2, type = DomainObject::class)
    private lateinit var anyFullyPopulatedDomainObjects: List<DomainObject>

    @Test
    fun `can inject random fully populated domain object`() {
        anyFullyPopulatedDomainObjects.shouldNotBeNull()
        anyFullyPopulatedDomainObjects.shouldNotBeEmpty()
        anyFullyPopulatedDomainObjects.forEach {
            it.shouldFullyPopulated()
        }
    }

    @RandomValue(size = 2, type = DomainObject::class, excludes = ["wotsits", "id", "nestedDomainObject.address"])
    private lateinit var anyPartiallyPopulatedDomainObject: List<DomainObject>

    @Test
    fun `can inject random partially populated domain object`() {
        anyPartiallyPopulatedDomainObject.shouldNotBeNull()
        anyPartiallyPopulatedDomainObject.shouldNotBeEmpty()
        anyPartiallyPopulatedDomainObject.forEach {
            it.shouldPartiallyPopulated()
        }
    }

    // JUnit 5의 Parallel mode 에서는 field 값은 한번에 정해지므로, 같은 값을 가지게 된다.
    // 이럴 때를 대비해 parameter로 제공하는 것이 가장 안전한 방식이다.
    @RepeatedTest(TEST_COUNT)
    fun `will inject a new random value each time`() {
        anyString.shouldNotBeNullOrEmpty()

        if (anyStrings.isEmpty()) {
            anyStrings.add(anyString)
        } else {
            anyStrings.shouldNotContain(anyString)
            anyStrings.add(anyString)
        }
    }
}