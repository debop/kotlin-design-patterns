package io.kommons.utils

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test
import java.util.Locale

class LocaleExtensionsTest {

    companion object: KLogging()

    @Test
    fun `get system default locale`() {
        if (Locale.getDefault() == Locale.KOREA) {
            Locale.KOREA.isDefault().shouldBeTrue()
        } else {
            Locale.KOREA.isDefault().shouldBeFalse()
        }
    }

    @Test
    fun `when null, return default`() {
        val nullValue: Locale? = null
        nullValue.orDefault() shouldEqual Locale.getDefault()
    }

    @Test
    fun `get parent locale`() {
        Locale.KOREA.parent shouldEqual Locale.KOREAN
        Locale.KOREAN.parent.shouldBeNull()

        Locale.US.parent shouldEqual Locale.ENGLISH
        Locale.UK.parent shouldEqual Locale.ENGLISH
    }

    @Test
    fun `get all parents`() {
        Locale.KOREA.parents shouldContainSame listOf(Locale.KOREA, Locale.KOREAN)
        Locale.KOREAN.parents shouldContain Locale.KOREAN

        Locale.US.parents shouldContainSame listOf(Locale.US, Locale.ENGLISH)
        Locale.ENGLISH.parents shouldContain Locale.ENGLISH
    }

    @Test
    fun `find locale files`() {
        val expected = listOf("msg_ko_KR", "msg_ko", "msg")

        val filenames = Locale.KOREA.calculateFilenames("msg")

        log.debug { "filenames=$filenames" }
        filenames shouldContainSame expected
    }
}