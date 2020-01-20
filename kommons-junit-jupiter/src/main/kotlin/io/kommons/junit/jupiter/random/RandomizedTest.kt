package io.kommons.junit.jupiter.random

import org.junit.jupiter.api.extension.ExtendWith

/**
 * RandomizedTest
 *
 *
 * ```
 * @RandomizedTest
 * class TestClass {
 *
 * @Test
 * fun `test with random value`(@RandomValue text:String) {
 *     // text is random string
 * }
 *
 * data class TestData(val name:String, val description:String, val amount:Double)
 *
 * @Test
 * fun `test with random list`(@RandomValue(type=TestData::class, size=10) testDatas:TestData) {
 *     // testDatas has random value TestData
 * }
 *
 * @author debop
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS,
        AnnotationTarget.FILE,
        AnnotationTarget.FUNCTION)
@MustBeDocumented
@Repeatable
@ExtendWith(RandomExtension::class)
annotation class RandomizedTest