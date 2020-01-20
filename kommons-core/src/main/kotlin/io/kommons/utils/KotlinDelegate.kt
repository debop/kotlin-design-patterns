package io.kommons.utils

import io.kommons.logging.KLogging
import io.kommons.logging.error
import java.lang.reflect.Constructor
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaConstructor
import kotlin.reflect.jvm.kotlinFunction

/**
 * Class의 기본 생성자를 반환합니다.
 */
fun <T: Any> Class<T>.primaryConstructor(): Constructor<T> = KotlinDelegate.primaryConstructor(this)

/**
 * Class의 기본 생성자를 찾습니다. 없으면 null을 반환
 */
fun <T: Any> Class<T>.findPrimaryConstructor(): Constructor<T>? = KotlinDelegate.findPrimaryConstructor(this)

/**
 * [args] 인자를 받는 생성자를 이용하여 객체를 생성합니다.
 */
fun <T: Any> Constructor<T>.instantiateClass(vararg args: Any?): T? = KotlinDelegate.instantiateClass(this, *args)

object KotlinDelegate: KLogging() {

    /**
     * 지정한 수형의 기본 생성자를 가져옵니다. 없으면 예외 [NoSuchElementException]를 발생시킵니다.
     * @receiver Class<T>
     * @return Constructor<T>
     */
    fun <T: Any> primaryConstructor(clazz: Class<T>): Constructor<T> =
        findPrimaryConstructor(clazz)
        ?: throw NoSuchElementException("Fail to find constructor for ${clazz.name}")

    /**
     * 지정한 수형의 기본 생성자 정보를 찾습니다. 없으면 null 반환
     * @receiver 수형 정보
     * @return 생성자 정보
     */
    fun <T: Any> findPrimaryConstructor(clazz: Class<T>): Constructor<T>? {
        return try {
            val primaryCtor = clazz.kotlin.primaryConstructor ?: return null
            primaryCtor.javaConstructor
            ?: error("Fail to find Java constructor for Kotlin primary constructor: ${clazz.name}")
        } catch (e: UnsupportedOperationException) {
            log.error(e) { "Fail to findl primary constructor of Kotlin class [${clazz.name}]" }
            null
        }
    }

    /**
     * 생성자 정보를 이용하여 `T` 수형의 인스턴스를 생성합니다.
     * @receiver Constructor<T>
     * @param args Array<out Any?> 생성자의 인자들
     * @return T? 생성된 인스턴스 또는 null
     */
    fun <T: Any> instantiateClass(constructor: Constructor<T>, vararg args: Any?): T? {

        val kotlinCtor = constructor.kotlinFunction ?: return constructor.newInstance(*args)

        val parameters = kotlinCtor.parameters
        val argParams = hashMapOf<KParameter, Any?>()

        check(args.size <= parameters.size) {
            "Number of provided argumenets should be less of equals than number of constructor parameters."
        }

        args.forEachIndexed { i, arg ->
            val isOptional = parameters[i].isOptional && arg == null
            if (!isOptional) {
                argParams[parameters[i]] = arg
            }
        }
        return kotlinCtor.callBy(argParams)
    }
}
