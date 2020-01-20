package io.kommons

import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.jupiter.api.Test

class SystemPropertyExtensionsTest {

    @Test
    fun `get and set system property`() {

        // 존재하지 않는 설정 값
        systemProperty["not.exists"]?.shouldBeEmpty()

        // 존재하는 시스템 설정
        systemProperty["user.dir"]!!.shouldNotBeEmpty()


        val kommonsGit = "https://github.com/kommons-projects"
        systemProperty["kommons.git"] = kommonsGit
        systemProperty["kommons.git"] shouldEqual kommonsGit
    }
}