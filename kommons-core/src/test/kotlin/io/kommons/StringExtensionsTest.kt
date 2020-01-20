package io.kommons

import io.kommons.junit.jupiter.random.RandomValue
import io.kommons.junit.jupiter.random.RandomizedTest
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeLessOrEqualTo
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldEndWith
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldStartWith
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

@RandomizedTest
class StringExtensionsTest {

    companion object: KLogging()

    val nullValue: String? = null
    val emptyValue: String = ""
    val blankValue: String = " \t "
    val someValue: String = "debop"

    @Test
    fun `is empty `() {
        nullValue.isNullOrEmpty().shouldBeTrue()

        emptyValue.isEmpty().shouldBeTrue()
        emptyValue.isNotEmpty().shouldBeFalse()

        blankValue.isEmpty().shouldBeFalse()
        blankValue.isNotEmpty().shouldBeTrue()

        someValue.isEmpty().shouldBeFalse()
        someValue.isNotEmpty().shouldBeTrue()
    }

    @Test
    fun `is whitespace`() {
        nullValue.isWhitespace().shouldBeTrue()
        nullValue.isNotWhitespace().shouldBeFalse()

        emptyValue.isWhitespace().shouldBeTrue()
        emptyValue.isNotWhitespace().shouldBeFalse()

        blankValue.isWhitespace().shouldBeTrue()
        blankValue.isNotWhitespace().shouldBeFalse()

        someValue.isWhitespace().shouldBeFalse()
        someValue.isNotWhitespace().shouldBeTrue()
    }

    @Test
    fun `has text`() {
        nullValue.hasText().shouldBeFalse()
        nullValue.noText().shouldBeTrue()

        emptyValue.hasText().shouldBeFalse()
        emptyValue.noText().shouldBeTrue()

        blankValue.hasText().shouldBeFalse()
        blankValue.noText().shouldBeTrue()

        someValue.hasText().shouldBeTrue()
        someValue.noText().shouldBeFalse()
    }

    @RepeatedTest(10)
    fun `convert to utf8`(@RandomValue origin: String) {

        val bytes = origin.toUtf8ByteArray()
        bytes.shouldNotBeEmpty()

        val actual = bytes.toUtf8String()
        actual shouldEqual origin
    }

    @Test
    fun `trim whitespace`() {
        blankValue.trimWhitespace().shouldBeEmpty()
        someValue.trimWhitespace() shouldEqual someValue

        " \t a \t ".trimWhitespace() shouldEqual "a"
    }

    @Test
    fun `trimStart whitespace`() {
        blankValue.trimStartWhitespace().shouldBeEmpty()
        someValue.trimStartWhitespace() shouldEqual someValue

        " \t a \t ".trimStartWhitespace() shouldEqual "a \t "
    }

    @Test
    fun `trimEnd whitespace`() {
        blankValue.trimEndWhitespace().shouldBeEmpty()
        someValue.trimEndWhitespace() shouldEqual someValue

        " \t a \t ".trimEndWhitespace() shouldEqual " \t a"
    }

    @Test
    fun `remove all whitespace`() {
        blankValue.removeWhitespace().shouldBeEmpty()
        someValue.removeWhitespace() shouldEqual someValue

        " a b\tc\t d".removeWhitespace() shouldEqual "abcd"
    }

    @Test
    fun `quote string`() {
        nullValue.quoted().shouldBeNull()
        emptyValue.quoted() shouldEqual """''"""
        blankValue.quoted() shouldEqual """'$blankValue'"""
        someValue.quoted() shouldEqual "'$someValue'"

        "debop's book".quoted() shouldEqual """'debop''s book'"""
        """''""".quoted() shouldEqual """''''''"""
        """'abc'""".quoted() shouldEqual """'''abc'''"""
    }

    @RepeatedTest(10)
    fun `string ellipsis`(@RandomValue str: String) {
        val origin = str.replicate(10)
        val length = origin.length

        origin.needEllipsis(length - 5).shouldBeTrue()
        origin.needEllipsis(length).shouldBeFalse()

        origin.ellipsisEnd(length - 5) shouldEndWith "..."
        origin.ellipsisEnd(length) shouldEqual origin

        origin.ellipsisStart(length - 5) shouldStartWith "..."
        origin.ellipsisStart(length) shouldEqual origin

        origin.ellipsisMid(length - 5) shouldContain "..."
        origin.ellipsisMid(length) shouldEqual origin
    }

    @Test
    fun `delete characters`() {
        val origin = "a.b.c.d/e.f"

        origin.deleteChars('.') shouldEqual "abcd/ef"
        origin.deleteChars('.', '/') shouldEqual "abcdef"
    }

    @Test
    fun `iterable as string list`() {
        listOf("1", 1, "2").asStringList() shouldContainSame listOf("1", "1", "2")
    }

    @Test
    fun `replicate string`() {
        emptyValue.replicate(10) shouldEqual emptyValue
        "a".replicate(5) shouldEqual "aaaaa"
        "a1".replicate(3) shouldEqual "a1a1a1"
    }

    @Test
    fun `get word count`() {
        "debop is developer and architecture".wordCount("developer") shouldEqualTo 1
        "debop is developer and architecture, anyone can be developer.".wordCount("developer") shouldEqualTo 2
    }

    @RepeatedTest(10)
    fun `get first line`(@RandomValue(type = String::class, size = 5) strs: List<String>) {
        val lines = strs.joinToString(LINE_SEPARATOR)

        lines.firstLine() shouldEqual strs[0]
    }

    @Test
    fun `sub string with string`() {
        val origin = "debop is developer and architecture"

        origin.between("developer", "architecture") shouldEqual " and "
        origin.between("debop", "developer") shouldEqual " is "

        origin.between("eb", "p is") shouldEqual "o"
    }

    @Test
    fun `drop charactors`() {
        emptyValue.dropFirst(3) shouldEqual emptyValue
        emptyValue.dropLast(3) shouldEqual emptyValue

        someValue.dropFirst(2) shouldEqual "bop"
        someValue.dropLast(2) shouldEqual "deb"

        someValue.dropFirst(100) shouldEqual ""
        someValue.dropLast(100) shouldEqual ""
    }

    @Test
    fun `take charactors`() {
        emptyValue.takeFirst(3) shouldEqual emptyValue
        emptyValue.takeLast(3) shouldEqual emptyValue

        someValue.takeFirst(2) shouldEqual "de"
        someValue.takeLast(2) shouldEqual "op"

        someValue.takeFirst(100) shouldEqual someValue
        someValue.takeLast(100) shouldEqual someValue
    }

    @Test
    fun `add prefix if absent`() {
        val prefix = "kommons."
        val expected = "kommons.version"

        "version".prefixIfAbsent(prefix, true) shouldEqual expected
        "kommons.version".prefixIfAbsent(prefix, true) shouldEqual expected
        "kommons.version".prefixIfAbsent(prefix, false) shouldEqual expected
    }

    @Test
    fun `add suffix if absent`() {
        val suffix = ".read"
        val expected = "version.read"

        "version".suffixIfAbsent(suffix, true) shouldEqual expected
        "version.read".suffixIfAbsent(suffix, true) shouldEqual expected
        "version".suffixIfAbsent(suffix, false) shouldEqual expected
    }

    @RepeatedTest(10)
    fun `get unique characters`(@RandomValue str: String) {
        val duplicated = str.repeat(3)

        val uniques = duplicated.uniqueChars()
        uniques.length shouldBeLessOrEqualTo str.length
        uniques.toSet().size shouldEqualTo uniques.length
    }
}