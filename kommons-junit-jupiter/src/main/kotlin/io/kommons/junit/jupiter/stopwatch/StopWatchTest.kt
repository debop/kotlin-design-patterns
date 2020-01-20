package io.kommons.junit.jupiter.stopwatch

import org.junit.jupiter.api.extension.ExtendWith

/**
 * StopWatchTest
 *
 * @author debop
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS,
        AnnotationTarget.FILE,
        AnnotationTarget.FUNCTION)
@MustBeDocumented
@Repeatable
@ExtendWith(StopWatchExtension::class)
annotation class StopWatchTest 