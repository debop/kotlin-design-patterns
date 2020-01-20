@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package io.kommons

import io.kommons.utils.Systemx
import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.StringUtils
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.util.regex.Pattern

private typealias JChar = Character

const val EMPTY_STRING = ""
const val TRIMMING = "..."
const val NULL_STRING = "<null>"
const val COMMA = ","
const val TAB = "\t"
private const val ElipsisLength = 80

@JvmField val LINE_SEPARATOR: String = Systemx.LineSeparator
@JvmField val WHITESPACE_BLOCK: Pattern = Pattern.compile("\\s+")
@JvmField val UTF_8: Charset = Charsets.UTF_8

fun CharSequence?.isWhitespace(): Boolean = isNullOrBlank()
fun CharSequence?.isNotWhitespace(): Boolean = !isWhitespace()

fun CharSequence?.hasText(): Boolean = !isNullOrEmpty() && !this!!.indices.any { this[it].isWhitespace() }
fun CharSequence?.noText(): Boolean = isNullOrEmpty() || this!!.indices.all { this[it].isWhitespace() }

fun String?.asNullIfEmpty(): String? = if (isNullOrEmpty()) null else this!!

/**
 * 문자열을 UTF-8 인코딩의 [ByteArray]로 변환합니다.
 * @receiver String
 * @return ByteArray
 */
fun String?.toUtf8ByteArray(): ByteArray = this?.run { toByteArray(Charsets.UTF_8) } ?: emptyByteArray

/**
 * [ByteArray]를 UTF-8 인코딩의 문자열로 반환한다
 */
fun ByteArray.toUtf8String(): String = toString(Charsets.UTF_8)

/**
 * [ByteBuffer]를 UTF-8 인코딩의 문자열로 반환한다
 */
fun ByteBuffer.toUtf8String(): String = Charsets.UTF_8.decode(this).toString()

/**
 * 문자열이 null이거나 empty라면 [fallback]을 수행합니다.
 */
inline fun String?.ifEmpty(fallback: () -> String): String = when {
    isNullOrEmpty() -> fallback()
    else            -> this!!
}

fun String?.hasLength(): Boolean = (this != null && length > 0)


/**
 * 문자열 앞 뒤의 Whitespace를 제거합니다.
 */
fun String.trimWhitespace(): String {
    if (isEmpty())
        return this.trim()

    val sb = StringBuilder(this.trim())
    while (sb.length > 0 && JChar.isWhitespace(sb[0])) {
        sb.deleteCharAt(0)
    }
    while (sb.length > 0 && JChar.isWhitespace(sb.last())) {
        sb.deleteCharAt(sb.lastIndex)
    }
    return sb.toString()
}

/**
 * 문자열 앞의 Whitespace를 제거합니다.
 */
fun String.trimStartWhitespace(): String {
    if (isEmpty())
        return this.trimStart()

    val sb = StringBuilder(this.trimStart())
    while (sb.length > 0 && JChar.isWhitespace(sb[0])) {
        sb.deleteCharAt(0)
    }
    return sb.toString()
}

/**
 * 문자열 뒷쪽의 Whitespace를 제거합니다.
 */
fun String.trimEndWhitespace(): String {
    if (isEmpty()) return this.trimEnd()

    val sb = StringBuilder(this.trimEnd())
    while (sb.length > 0 && JChar.isWhitespace(sb.last())) {
        sb.deleteCharAt(sb.lastIndex)
    }
    return sb.toString()
}

/**
 * 문자열의 모든 곳의 Whitespace를 제거합니다.
 */
fun String.removeWhitespace(): String {
    if (isEmpty()) return this.trim()

    return buildString(length) {
        this@removeWhitespace
            .filterNot { JChar.isWhitespace(it) }
            .forEach { append(it) }
    }
}

fun String?.quoted(): String? {
    if (this == null)
        return null

    return if (isEmpty()) "''"
    else "'" + replace("\'", "\'\'") + "'"
}

@JvmOverloads
fun randomString(size: Int = 10): String {
    size.assertZeroOrPositiveNumber("size")
    return RandomStringUtils.randomAlphanumeric(size)
}

fun String?.needEllipsis(maxLength: Int = ElipsisLength): Boolean {
    return this != null && isNotBlank() && length > maxLength
}

fun String?.ellipsisEnd(maxLength: Int = ElipsisLength): String =
    this?.run {
        return if (!this.needEllipsis(maxLength)) this
        else substring(0, maxLength - TRIMMING.length) + TRIMMING
    } ?: EMPTY_STRING


fun String?.ellipsisMid(maxLength: Int = ElipsisLength): String =
    this?.run {
        if (!needEllipsis(maxLength))
            return this

        val length = maxLength / 2
        val sb = StringBuilder()
        sb.append(this.substring(0, length)).append(TRIMMING)

        val len = if (maxLength % 2 == 0) this.length - length
        else this.length - length - 1

        sb.append(this.substring(len))
        return sb.toString()
    } ?: EMPTY_STRING

fun String?.ellipsisStart(maxLength: Int = ElipsisLength): String =
    this?.run {
        return if (!this.needEllipsis(maxLength)) this
        else TRIMMING + this.substring(this.length - maxLength + TRIMMING.length)
    } ?: EMPTY_STRING


fun CharSequence?.deleteChars(vararg chars: Char): String {
    if (isNullOrEmpty()) {
        return EMPTY_STRING
    }
    if (chars.isEmpty()) {
        return this.toString()
    }
    return this!!.filterNot { chars.contains(it) }.toString()
}

/**
 * 컬렉션의 요소를 문자열로 변환하여, 문자열 컬렉션으로 반환합니다.
 */
@JvmOverloads
fun <T: Any> Iterable<T>.asStringList(defaultValue: String = EMPTY_STRING): List<String> =
    map { it.asString(defaultValue) }


fun CharSequence?.replicate(n: Int): String {
    if (this.isNullOrEmpty())
        return EMPTY_STRING

    return this.repeat(n)
}

fun CharSequence?.wordCount(word: String): Int {
    return StringUtils.countMatches(this, word)
}

@JvmOverloads
fun CharSequence?.firstLine(lineSeparator: String = LINE_SEPARATOR): String {
    if (this.isNullOrBlank())
        return EMPTY_STRING

    val index = this.indexOf(lineSeparator)
    return if (index > 0) substring(0, index) else this.toString()
}

/**
 * 문자열에서 [start] 문자열과 [end]문자열 사이의 문자열을 추출합니다. (start와 end는 제외됩니다)
 *
 * ```kotlin
 *
 * val origin = "debop is developer and architecture"
 *
 * origin.between("developer", "architecture") shouldEqual " and "
 * origin.between("debop", "developer") shouldEqual " is "
 *
 * origin.between("eb", "p is") shouldEqual "o"
 *
 * ```
 *
 * @param start 시작 문자열
 * @param end 끝 문자열
 * @return 시작문자열과 끝 문자열 사이의 문자열
 */
fun CharSequence?.between(start: String, end: String): String {
    if (this.isNullOrBlank())
        return this?.toString() ?: EMPTY_STRING

    if (areEquals(start, end))
        return EMPTY_STRING

    var startIndex = 0
    if (start.isNotEmpty()) {
        val index = this.indexOf(start)
        if (index >= 0)
            startIndex = index + start.length
    }

    var endIndex = this.length
    if (end.isNotEmpty()) {
        val index = this.indexOf(end, startIndex)
        if (index >= 0)
            endIndex = index
    }

    return if (endIndex >= startIndex) this.substring(startIndex, endIndex) else EMPTY_STRING
}


@JvmOverloads
fun String.dropFirst(count: Int = 1): String =
    if (count < length) this.substring(count)
    else EMPTY_STRING


@JvmOverloads
fun String.dropLast(count: Int = 1): String =
    if (count < length) this.substring(0, this.length - count)
    else EMPTY_STRING

@JvmOverloads
fun String.takeFirst(count: Int = 1): String =
    if (count < length) this.substring(0, count)
    else this

@JvmOverloads
fun String.takeLast(count: Int = 1): String =
    if (count < length) this.substring(this.length - count)
    else this

/**
 * 지정한 접두사로 시작하지 않는다면 접두사를 추가합니다.
 *
 * @param prefix 접두사
 * @param ignoreCase 대소문자 구분 여부 (기본: false)
 * @return [prefix]가 접두사로 붙은 문자열
 */
@JvmOverloads
fun String.prefixIfAbsent(prefix: String, ignoreCase: Boolean = false): String =
    if (this.startsWith(prefix, ignoreCase)) this else prefix + this

/**
 * 지정한 접미사로 끝나지 않는다면 접미사를 추가합니다.
 *
 * @param suffix 접미사
 * @param ignoreCase 대소문자 구분 여부 (기본: false)
 * @return [suffix]가 접미사로 붙은 문자열
 */
@JvmOverloads
fun String.suffixIfAbsent(suffix: String, ignoreCase: Boolean = false): String =
    if (this.endsWith(suffix, ignoreCase)) this else this + suffix

/**
 * 문자열의 문자들 중 유니크한 문자로만 필터링해서 문자열로 반환합니다.
 */
fun CharSequence.uniqueChars(): String = buildString {
    this@uniqueChars.forEach { char ->
        if (char != ' ' && !contains(char)) {
            append(char)
        }
    }
}

fun CharSequence.sliding(size: Int): Sequence<CharSequence> = sequence {
    size.assertPositiveNumber("size")

    val self = this@sliding
    var start = 0
    var end = start + size
    while (end <= self.length) {
        yield(self.subSequence(start++, end++))
    }
}

fun String.sliding(size: Int): Sequence<String> = sequence {
    size.assertPositiveNumber("size")

    val self = this@sliding
    var start = 0
    var end = start + size
    while (end <= self.length) {
        yield(self.substring(start++, end++))
    }
}