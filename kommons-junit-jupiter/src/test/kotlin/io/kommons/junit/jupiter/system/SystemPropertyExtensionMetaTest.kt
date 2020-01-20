package io.kommons.junit.jupiter.system

import io.kommons.junit.jupiter.utils.ExtensionTester
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.junit.platform.engine.discovery.DiscoverySelectors.selectClass
import org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod

/**
 * 다른 테스트 클래스와 메소드를 수행하고, 시스템 속성이 원복되는지 확인한다
 */
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class SystemPropertyExtensionMetaTest {

    @Test
    fun `클래스 단위로 시스템 속성은 원복된다`() {
        // 원형 속성
        System.setProperty("classPropertyC", "no")

        //테스트 수행 시에는 classPropertyValueC 로 설정된다
        ExtensionTester.execute(selectClass(SystemPropertyExtensionClassTest::class.java))

        // 복원된 속성
        System.getProperty("classPropertyC") shouldEqual "no"
    }

    @Test
    fun `클래스 단위로 시스템 속성들이 원복된다`() {
        // 원형 속성
        System.setProperty("classPropertyA", "no")
        System.setProperty("classPropertyB", "yes")

        //테스트 수행 시에는 classPropertyValueC 로 설정된다
        ExtensionTester.execute(selectClass(SystemPropertyExtensionClassTest::class.java))

        // 복원된 속성
        System.getProperty("classPropertyA") shouldEqual "no"
        System.getProperty("classPropertyB") shouldEqual "yes"
    }

    @Test
    fun `메소드 단위로 시스템 속성은 원복된다`() {
        // 원형 속성
        System.setProperty("keyC", "no")

        //테스트 수행 시에는 classPropertyValueC 로 설정된다
        ExtensionTester.execute(selectMethod(SystemPropertyExtensionClassTest::class.java, "메소드 단위로 테스트를 위한 시스템 속성을 설정"))

        // 복원된 속성
        System.getProperty("keyC") shouldEqual "no"
    }

    @Test
    fun `메소드 단위로 테스트 시, 기존 속성이 없는 경우 null 로 리셋된다`() {
        // 원형 속성
        System.clearProperty("keyC")

        //테스트 수행 시에는 classPropertyValueC 로 설정된다
        ExtensionTester.execute(selectMethod(SystemPropertyExtensionClassTest::class.java, "메소드 단위로 테스트를 위한 시스템 속성을 설정"))

        // 복원된 속성
        System.getProperty("keyC").shouldBeNull()
    }
}