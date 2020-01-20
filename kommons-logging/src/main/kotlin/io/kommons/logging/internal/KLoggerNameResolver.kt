package io.kommons.logging.internal

import java.lang.reflect.Modifier

/**
 * KLoggerNameResolver
 *
 * @author debop
 */
internal object KLoggerNameResolver {

    internal fun name(action: () -> Unit): String {
        val name = action.javaClass.name

        return when {
            name.contains("Kt$") -> name.substringBefore("Kt$")
            name.contains("$")   -> name.substringBefore("$")
            else                 -> name
        }
    }

    internal fun <T: Any> name(forClass: Class<T>): String =
        unwrapCompanionClass(forClass).name

    private fun <T: Any> unwrapCompanionClass(clazz: Class<T>): Class<*> {
        if (clazz.enclosingClass != null) {
            try {
                val field = clazz.enclosingClass.getField(clazz.simpleName)
                if (Modifier.isStatic(field.modifiers) && field.type == clazz) {
                    return clazz.enclosingClass
                }
            } catch (e: Throwable) {
                // It is not a companion object
            }
        }

        return clazz
    }
}