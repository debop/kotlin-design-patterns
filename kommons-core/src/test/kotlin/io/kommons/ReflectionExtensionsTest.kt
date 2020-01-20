package io.kommons

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test

class ReflectionExtensionsTest {

    @Test
    fun `class is present`() {
        classIsPresent("java.lang.String").shouldBeTrue()
        classIsPresent(ValueObject::class.qualifiedName!!).shouldBeTrue()
    }

    @Test
    fun `class is present in other jar`() {
        classIsPresent("org.slf4j.LoggerFactory").shouldBeTrue()
        classIsPresent(KLogging::class.qualifiedName!!).shouldBeTrue()
    }

    @Test
    fun `class is not exists`() {
        classIsPresent("xyz.NotExisting").shouldBeFalse()
    }

    @Test
    fun `존재하는 클래스를 인스턴싱합니다`() {
        newInstanceOrNull<KLogging>("io.kommons.logging.KLogging").shouldNotBeNull()

        RuntimeException::class.newInstanceOrNull().shouldNotBeNull()
        RuntimeException::class.java.newInstanceOrNull().shouldNotBeNull()
    }

    @Test
    fun `존재하지 않는 클래스를 생성하고자하면 null을 반환한다`() {
        classIsPresent("com.google.gson.Gson").shouldBeFalse()
        newInstanceOrNull<Any>("com.google.gson.Gson").shouldBeNull()
    }
}