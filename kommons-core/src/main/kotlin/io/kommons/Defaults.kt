package io.kommons

import java.nio.charset.Charset
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Locale

/**
 * System Default Locale
 */
@JvmField
val defaultLocale: Locale = Locale.getDefault()

/**
 * System Default Charset (UTF_8)
 */
@JvmField
val defaultCharset: Charset = Charsets.UTF_8

/**
 * System default charset name ("UTF-8")
 */
@JvmField
val defaultCharsetName: String = defaultCharset.name()

/**
 * System Default Charactor encoding ("utf-8")
 */
@JvmField
val defaultEncoding: String = defaultCharsetName.toLowerCase()

/**
 * System default [ZoneId]
 *
 * @see defaultZoneOffset
 */
@JvmField
val defaultZoneId: ZoneId = ZoneId.systemDefault()

/**
 * UTC [ZoneId]
 *
 * @see utcZoneOffset
 */
@JvmField
val utcZoneId: ZoneId = ZoneId.of("UTC")

/**
 * System default [ZoneOffset]
 *
 * @see defaultZoneId
 */
@JvmField
val defaultZoneOffset: ZoneOffset = ZoneOffset.of(defaultZoneId.id)

/**
 * UTC [ZoneOffset]
 *
 * @see utcZoneId
 */
@JvmField
val utcZoneOffset: ZoneOffset = ZoneOffset.UTC