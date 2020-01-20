package io.kommons.utils

import io.kommons.assertNotBlank
import io.kommons.logging.KotlinLogging
import io.kommons.logging.debug
import io.kommons.logging.trace
import java.util.Locale

private val log = KotlinLogging.logger {}

/** 현 Locale 이 기본 Locale 인가 */
fun Locale?.isDefault(): Boolean = Locale.getDefault() == this

/** 지정한 Locale 이 null 이면 기본 Locale 을 반환합니다 */
fun Locale?.orDefault(): Locale = this ?: Locale.getDefault()

/** [Locale] 의 부모 Locale을 구합니다. */
val Locale?.parent: Locale?
    get() {
        return this?.let {
            return when {
                variant.isNotEmpty() && (language.isNotEmpty() || country.isNotEmpty()) -> Locale(language, country)
                country.isNotEmpty()                                                    -> Locale(language)
                else                                                                    -> null
            }
        }
    }

/** [Locale] 의 조상 Locale 들을 구합니다. */
val Locale?.parents: List<Locale>
    get() {
        val results = mutableListOf<Locale>()
        this?.let {
            var current = this
            while (current != null) {
                results.add(0, current)
                current = current.parent
            }
        }
        return results
    }

/**
 * Calculate the filenames for the given bundle basename and Locale,
 * appending language code, country code, and variant code.
 * E.g.: basename "messages", Locale "de_AT_oo" -> "messages_de_AT_OO",
 * "messages_de_AT", "messages_de".
 * <p>Follows the rules defined by {@link java.util.Locale#toString()}.
 *
 * @param basename the basename of the bundle
 * @return the List of filenames to check
 */
fun Locale?.calculateFilenames(basename: String): List<String> {

    if (this == null)
        return emptyList()

    basename.assertNotBlank("basename")
    log.trace { "Locale에 해당하는 파일명을 조합합니다. basename=$basename, locale=$this" }

    val results = mutableListOf<String>()

    val language = this.language
    val country = this.country
    val variant = this.variant

    log.trace { "language=$language, country=$country, variant=$variant" }

    val temp = StringBuilder(basename)
    temp.append("_")

    if (language.isNotEmpty()) {
        temp.append(language)
        results.add(0, temp.toString())
    }

    temp.append("_")

    if (country.isNotEmpty()) {
        temp.append(country)
        results.add(0, temp.toString())
    }

    if (variant.isNotEmpty() && (language.isNotEmpty() || country.isNotEmpty())) {
        temp.append("_").append(variant)
        results.add(0, temp.toString())
    }
    results.add(basename)

    log.debug { "Locale에 해당하는 파일명을 조합했습니다. basename=$basename, locale=$this, results=$results" }

    return results
}
