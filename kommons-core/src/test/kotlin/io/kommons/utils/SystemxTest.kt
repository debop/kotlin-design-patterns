package io.kommons.utils

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import io.kommons.logging.trace
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.jupiter.api.Test

/**
 * SystemxTest
 *
 * @author debop
 */
class SystemxTest {

    companion object: KLogging()

    @Test
    fun `자바 버전 조회`() {
        log.trace { "Java implementation version=${Systemx.JavaImplementationVersion}" }
        // Systemx.JavaVersion.shouldNotBeEmpty()
        Systemx.JavaHome.shouldNotBeEmpty()
    }

    @Test
    fun `시스템 설정 정보`() {

        log.debug { "Line separator = ${Systemx.LineSeparator}" }
        log.debug { "File separator = ${Systemx.FileSeparator}" }
        log.debug { "Path separator = ${Systemx.PathSeparator}" }
        log.debug { "File encoding = ${Systemx.FileEncoding}" }

        log.debug { "Temp Dir = ${Systemx.TempDir}" }
        log.debug { "User Dir = ${Systemx.UserDir}" }
    }
}