package io.kommons.junit.jupiter.random

import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import io.kommons.logging.KLogging
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.junit.jupiter.api.extension.TestInstancePostProcessor
import java.util.stream.Stream
import kotlin.streams.asSequence
import kotlin.streams.toList

/**
 * RandomExtension
 *
 * @author debop
 */
class RandomExtension: TestInstancePostProcessor, ParameterResolver {

    companion object: KLogging() {
        private val random: EnhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
            .objectPoolSize(100)
            .randomizationDepth(3)
            .charset(Charsets.UTF_8)
            .stringLengthRange(1, 125)
            .collectionSizeRange(5, 20)
            .scanClasspathForConcreteTypes(true)
            .overrideDefaultInitialization(true)
            .ignoreRandomizationErrors(true)
            .build()

        private fun resolve(targetType: Class<*>, annotation: RandomValue): Any = when {
            targetType.isAssignableFrom(List::class.java) || targetType.isAssignableFrom(Collection::class.java) ->
                random.objects(annotation.type.java, annotation.size, *annotation.excludes).toList()
            targetType.isAssignableFrom(Set::class.java)                                                         ->
                random.objects(annotation.type.java, annotation.size, *annotation.excludes).toList().toSet()
            targetType.isAssignableFrom(Stream::class.java)                                                      ->
                random.objects(annotation.type.java, annotation.size, *annotation.excludes)
            targetType.isAssignableFrom(Sequence::class.java)                                                    ->
                random.objects(annotation.type.java, annotation.size, *annotation.excludes).asSequence()
            else                                                                                                 ->
                random.nextObject(targetType, *annotation.excludes)
        }
    }

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Boolean {
        return parameterContext.parameter.getAnnotation(RandomValue::class.java) != null
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        return with(parameterContext.parameter) {
            resolve(type, getAnnotation(RandomValue::class.java))
        }
    }

    override fun postProcessTestInstance(testInstance: Any, context: ExtensionContext) {
        testInstance.javaClass.declaredFields.forEach { field ->
            val annotation = field.getAnnotation(RandomValue::class.java)
            if (annotation != null) {
                if (!field.isAccessible) {
                    field.isAccessible = true
                }
                val randomObject = resolve(field.type, annotation)
                field.set(testInstance, randomObject)
            }
        }
    }


}