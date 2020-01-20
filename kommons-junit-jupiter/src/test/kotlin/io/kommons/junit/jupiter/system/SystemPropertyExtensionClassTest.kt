package io.kommons.junit.jupiter.system

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode

@SystemProperties([
                      SystemProperty("classPropertyA", "classPropertyValueA"),
                      SystemProperty("classPropertyB", "classPropertyValueB"),
                      SystemProperty("classPropertyC", "classPropertyValueC")
                  ])
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class SystemPropertyExtensionClassTest {

    @Test
    fun `클래스 단위로 테스트를 위한 시스템 속성을 설정`() {
        System.getProperty("classPropertyA") shouldEqual "classPropertyValueA"
        System.getProperty("classPropertyB") shouldEqual "classPropertyValueB"
        System.getProperty("classPropertyC") shouldEqual "classPropertyValueC"
    }

    @Test
    @SystemProperties([
                          SystemProperty("keyA", "valueA"),
                          SystemProperty("keyB", "valueB")
                      ])
    fun `메소드 단위로 테스트를 위한 시스템 속성을 설정`() {
        System.getProperty("classPropertyA") shouldEqual "classPropertyValueA"
        System.getProperty("classPropertyB") shouldEqual "classPropertyValueB"
        System.getProperty("classPropertyC") shouldEqual "classPropertyValueC"

        System.getProperty("keyA") shouldEqual "valueA"
        System.getProperty("keyB") shouldEqual "valueB"
    }

    @Test
    @SystemProperties([
                          SystemProperty("keyA", "valueA-1"),
                          SystemProperty("keyB", "valueB-1"),
                          SystemProperty("keyC", "valueC-1")
                      ])
    @SystemProperty("keyD", "valueD-1")
    fun `다른 메소드의 시스템 속성을 재정의`() {
        System.getProperty("keyA") shouldEqual "valueA-1"
        System.getProperty("keyB") shouldEqual "valueB-1"
        System.getProperty("keyC") shouldEqual "valueC-1"
        System.getProperty("keyD") shouldEqual "valueD-1"
    }
}