package io.kommons.junit.jupiter.system

import io.kommons.junit.jupiter.store
import io.kommons.logging.KLogging
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.platform.commons.util.AnnotationUtils
import java.lang.reflect.AnnotatedElement

/**
 * 테스트 시 시스템 속성 정보를 임시로 설정하고, 테스트 후에는 원래 시스템 속성 값으로 복구시키는 기능을 제공합니다.
 *
 * @see SystemProperty
 * @see SystemProperties
 *
 * @author debop
 */
class SystemPropertyExtension: BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    companion object: KLogging() {
        private const val KEY_PREFIX = "io.kommons.junit.jupiter.restoreContext."

        private fun makeKey(key: String): String = KEY_PREFIX + key
    }

    override fun beforeAll(context: ExtensionContext) {
        val systemProperties = getSystemProperties(context.requiredTestClass)
        if (systemProperties.isNotEmpty()) {
            val restoreContext = buildRestoreContext(systemProperties)
            writeRestoreContextInClass(context, restoreContext)
        }
    }

    override fun afterAll(context: ExtensionContext) {
        readRestoreContextInClass(context)?.restore()
    }


    override fun beforeEach(context: ExtensionContext) {
        val systemProperties = getSystemProperties(context.requiredTestMethod)
        if (systemProperties.isNotEmpty()) {
            val restoreContext = buildRestoreContext(systemProperties)
            writeRestoreContextInMethod(context, restoreContext)
        }
    }

    override fun afterEach(context: ExtensionContext) {
        readRestoreContextInMethod(context)?.restore()
    }

    private fun buildRestoreContext(systemProperties: List<SystemProperty>): RestoreContext {
        val builder = RestoreContext.Builder()

        // 테스트 시 설정할 시스템 속성 정보
        systemProperties.forEach { property ->
            // 테스트 후 리셋할 시스템 속성 이름을 기록함
            builder.addPropertyName(property.name)

            val oldValue = System.getProperty(property.name)
            if (oldValue != null) {
                // 테스트 후에 기존 속성을 복원하기 위해 기록함
                builder.addRestoreProperty(property.name, oldValue)
            }
            System.setProperty(property.name, property.value)
        }

        return builder.build()
    }

    private fun getSystemProperties(annotatedElement: AnnotatedElement): List<SystemProperty> {
        val result = mutableListOf<SystemProperty>()

        if (AnnotationUtils.isAnnotated(annotatedElement, SystemProperties::class.java)) {
            result.addAll(annotatedElement.getAnnotation(SystemProperties::class.java).value)
        }
        if (AnnotationUtils.isAnnotated(annotatedElement, SystemProperty::class.java)) {
            result.add(annotatedElement.getAnnotation(SystemProperty::class.java))
        }
        return result
    }

    private fun readRestoreContextInClass(context: ExtensionContext): RestoreContext? {
        val key = makeKey(context.requiredTestClass.name)
        return context.store(this.javaClass).get(key, RestoreContext::class.java)
    }

    private fun writeRestoreContextInClass(context: ExtensionContext, restoreContext: RestoreContext) {
        val key = makeKey(context.requiredTestClass.name)
        context.store(this.javaClass).getOrComputeIfAbsent(key) { restoreContext }
    }

    private fun readRestoreContextInMethod(context: ExtensionContext): RestoreContext? {
        val key = makeKey(context.requiredTestMethod.name)
        return context.store(this.javaClass).get(key, RestoreContext::class.java)
    }

    private fun writeRestoreContextInMethod(context: ExtensionContext, restoreContext: RestoreContext) {
        val key = makeKey(context.requiredTestMethod.name)
        context.store(this.javaClass).getOrComputeIfAbsent(key) { restoreContext }
    }


}