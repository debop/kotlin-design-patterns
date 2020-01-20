package io.kommons.utils

import io.kommons.logging.KLogging

/**
 * 런타임 시 Kotlin 환경인지 파악합니다.
 *
 * @author debop
 */
@Suppress("UNCHECKED_CAST")
object KotlinDetector: KLogging() {

    val kotlinMetadata: Class<out Annotation>? by lazy {
        try {
            Class.forName("kotlin.Metadata", false, KotlinDetector::class.java.classLoader)
        } catch (e: ClassNotFoundException) {
            null
        } as? Class<out Annotation>
    }

    /**
     * 현 프로세스에서 Kotlin을 사용할 수 있는지 알려준다
     */
    fun isKotlinPresent(): Boolean = kotlinMetadata != null

    /**
     * 지정한 수형이 Kotlin 으로 정의된 수형인가 판단합니다.
     *
     * @param clazz Class<*> 검사할 수형
     * @return Boolean 수형이 Kotlin으로 정의되었다면 true, 아니면 False를 반환
     */
    fun isKotlinType(clazz: Class<*>): Boolean =
        isKotlinPresent() && clazz.getDeclaredAnnotation(kotlinMetadata) != null
}

/**
 * 현 수형이 Kotlin 으로 정의된 수형인가 판단합니다.
 * @receiver Class<*> 검사할 수형
 * @return Boolean 수형이 Kotlin으로 정의되었다면 true, 아니면 False를 반환
 */
fun Class<*>.isKotlinType(): Boolean = KotlinDetector.isKotlinType(this)