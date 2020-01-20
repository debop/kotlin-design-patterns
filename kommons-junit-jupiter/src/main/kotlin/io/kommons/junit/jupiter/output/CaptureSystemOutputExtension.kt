package io.kommons.junit.jupiter.output

import io.kommons.junit.jupiter.store
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.junit.platform.commons.support.ReflectionSupport

/**
 * 테스트 시 Console에 출력되는 정보를 capture 해서 [OutputCapture]에 제공합니다.
 *
 * @author debop
 */
class CaptureSystemOutputExtension: BeforeEachCallback, AfterEachCallback, ParameterResolver {

    override fun beforeEach(context: ExtensionContext) {
        getOutputCapture(context).startCapture()
    }

    override fun afterEach(context: ExtensionContext) {
        getOutputCapture(context).finishCapture()
    }

    override fun supportsParameter(parameterContext: ParameterContext,
                                   extensionContext: ExtensionContext): Boolean {
        return extensionContext.testMethod.isPresent &&
               parameterContext.parameter.type == OutputCapture::class.java
    }

    override fun resolveParameter(parameterContext: ParameterContext,
                                  extensionContext: ExtensionContext): Any {
        return getOutputCapture(extensionContext)
    }


    private fun getOutputCapture(context: ExtensionContext): OutputCapture {
        return context.store(this.javaClass)
            .getOrComputeIfAbsent(OutputCapture::class.java,
                                  { ReflectionSupport.newInstance(it) },
                                  OutputCapture::class.java)
    }
}