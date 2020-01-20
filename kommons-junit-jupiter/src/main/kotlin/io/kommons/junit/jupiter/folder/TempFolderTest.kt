package io.kommons.junit.jupiter.folder

import org.junit.jupiter.api.extension.ExtendWith

/**
 * TemporaryFolderTest
 *
 * @author debop
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS,
        AnnotationTarget.FILE,
        AnnotationTarget.FUNCTION)
@MustBeDocumented
@Repeatable
@ExtendWith(TempFolderExtension::class)
annotation class TempFolderTest