package io.kommons.designpatterns.singleton

/**
 * Kotlin lazy delegator를 이용하여 singleton 을 생성합니다.
 *
 * @author debop
 */
class KotlinSingleton {

    companion object {

        @JvmStatic
        val INSTANCE: KotlinSingleton by lazy { KotlinSingleton() }
    }
}