package io.kommons.junit.jupiter.system

import org.junit.jupiter.api.extension.ExtendWith

/**
 * 테스트 시에 시스템 속성을 지정하고, 테스트 후에는 원복한다
 *
 * ```kotlin
 * @SystemProperties(
 *     SystemProperty("name="nameA", "valueA"),
 *     SystemProperty("name="nameB", "valueB")
 * )
 * ```
 * @property value Array<SystemProperty>
 * @constructor
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS,
        AnnotationTarget.FILE,
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.ANNOTATION_CLASS)
@MustBeDocumented
@Repeatable
@ExtendWith(SystemPropertyExtension::class)
annotation class SystemProperties(val value: Array<SystemProperty>)