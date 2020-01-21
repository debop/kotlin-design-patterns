package io.kommons.designpatterns.lazy.loading

import io.kommons.logging.KLogging

/**
 * Kotlin property delegate 기능 중 `by lazy`를 이용하여 지연 로딩을 수행합니다.
 */
class KotlinHolder {

    companion object: KLogging()

    private val _heavy: Heavy by lazy { Heavy() }

    fun getHeavy(): Heavy = _heavy
}