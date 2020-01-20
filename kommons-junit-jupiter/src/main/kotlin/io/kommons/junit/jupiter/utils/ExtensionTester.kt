package io.kommons.junit.jupiter.utils

import org.junit.jupiter.engine.JupiterTestEngine
import org.junit.platform.engine.DiscoverySelector
import org.junit.platform.engine.ExecutionRequest
import org.junit.platform.engine.UniqueId
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder

/**
 * 테스트 클래스를 Jupiter engine 으로 실행할 수 있도록 합니다.
 *
 * @author debop
 */
object ExtensionTester {

    /**
     * 테스트 클래스를 Jupiter engine으로 수행합니다.
     *
     * ```
     * ExtensionTester.execute(selectClass(SystemPropertyExtensionClassTest::class.java))
     * ```
     * @param selectors Array<out DiscoverySelector>
     * @return RecordingExecutionListener
     */
    fun execute(vararg selectors: DiscoverySelector): RecordingExecutionListener {

        val testEngine = JupiterTestEngine()

        // 요청된 테스트 리소스를 찾습니다.
        val discoveryRequest = LauncherDiscoveryRequestBuilder
            .request()
            .selectors(*selectors)
            .build()
        val listener = RecordingExecutionListener()

        // 찾은 테스트 리소스를 실행합니다.
        val testDescriptor = testEngine.discover(discoveryRequest, UniqueId.forEngine(testEngine.id))
        testEngine.execute(ExecutionRequest(testDescriptor, listener, discoveryRequest.configurationParameters))

        return listener
    }
}