package io.kommons.junit.jupiter.random

import kotlin.reflect.KClass

/**
 * Allows the caller to customise the random generation of a given type.
 * NOTE: Can't support private field. just assing to public field
 *
 * <p>Usage example:
 *
 * ```kotlin
 *  // create a random instance of String
 *  @RandomValue lateinit var anyString:String?
 *
 *  // create a random, fully populated instance of MyDomainObject
 *  @RandomValue lateinit var fullyPopulatedDomainObject: DomainObject
 *
 *  // create a random, partially populated instance of MyDomainObject, ignoring these fields: "wotsits", "id", "nestedDomainObject.address"
 *  @RandomValue(excludes = ["wotsits", "id", "nestedDomainObject.address"]) lateinit var partiallyPopulatedDomainObject: MyDomainObject
 *
 *  // create a List containing the default size of randomly generated instances of String
 *  @RandomValue(type = String::class) lateinit var anyStrings: List<String>
 *
 *  // create a Stream containing two randomly generated instances of MyDomainObject
 *  @RandomValue(size = 2, type = MyDomainObject::class) lateinit var anyStrings: Stream<MyDomainObject>
 * ```
 *
 * @property excludes  When generating a random type you may want to exclude some properties
 * @property size      When generating a collection of random type you may want to limit its size.
 * @property type      When generating a collection of random type you'll want to tell the generator what that type
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD,
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.ANNOTATION_CLASS)
@MustBeDocumented
annotation class RandomValue(val excludes: Array<String> = [],
                             val size: Int = 10,
                             val type: KClass<*> = Any::class)