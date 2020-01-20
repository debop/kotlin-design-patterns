package io.kommons

/**
 * Enum 정보를 name to enum value 의 map으로 빌드합니다.
 */
fun <E: Enum<E>> Class<E>.enumMap(): Map<String, E> =
    this.enumConstants.map { it.name to it }.toMap()

/**
 * Enum 값 들을 List로 봔환합니다.
 */
fun <E: Enum<E>> Class<E>.enumList(): List<E> = this.enumConstants.toList()

/**
 * Enum 값을 [name]으로 검색합니다.
 */
fun <E: Enum<E>> Class<E>.getByName(name: String): E? =
    this.enumConstants.firstOrNull { it.name == name }

/**
 * Enum 값 중에 [name]을 가지는 값이 존재하는지 검색합니다.
 */
fun <E: Enum<E>> Class<E>.isValidName(name: String): Boolean =
    runCatching { getByName(name) != null }.getOrDefault(false)
