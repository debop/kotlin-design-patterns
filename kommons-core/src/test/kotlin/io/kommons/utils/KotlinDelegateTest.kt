package io.kommons.utils

import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test
import java.util.Optional
import kotlin.reflect.full.primaryConstructor

@Suppress("UNUSED_PARAMETER")
class KotlinDelegateTest {

    @Test
    fun `immutable class 생성하기`() {

        val ctor = Foo::class.java.findPrimaryConstructor()!!
        val foo = ctor.instantiateClass("a", 3)!!

        foo.param1 shouldEqual "a"
        foo.param2 shouldEqual 3
    }

    @Test
    fun `named parameter를 생성자로 가지는 immutable class 생성`() {
        val ctor = Bar::class.java.findPrimaryConstructor()!!
        val bar = ctor.instantiateClass("a", 8)!!

        bar.param1 shouldEqual "a"
        bar.param2 shouldEqual 8

        val bar2 = ctor.instantiateClass("b")!!

        bar2.param1 shouldEqual "b"
        bar2.param2 shouldEqual 12
    }

    @Test
    fun `Optional parameter를 생성자로 가지는 immutable class 생성`() {
        val ctor = Baz::class.java.findPrimaryConstructor()!!
        val bar = ctor.instantiateClass()!!

        bar.param1 shouldEqual "a"
        bar.param2 shouldEqual Optional.empty()
    }

    @Test
    fun `empty-arg construtor 생성자`() {
        val primary = TwoConstructorsWithDefaultOne::class.primaryConstructor
        primary.shouldBeNull()
    }

    @Test
    fun `인자 1개, 2개를 가진 생성자`() {
        val primary = TwoConstructorsWithoutDefaultOne::class.primaryConstructor
        primary.shouldBeNull()
    }

    @Test
    fun `빈 인자를 받는 생성자 한개만 있는 클래스`() {
        val primary = OneConstructorWithDefaultOne::class.primaryConstructor
        primary.shouldBeNull()
    }

    @Test
    fun `한개의 인자를 받는 생성자 한개가 있는 클래스`() {
        val primary = OneConstructorWithoutDefaultOne::class.primaryConstructor
        primary.shouldBeNull()
    }

    class Foo(val param1: String, val param2: Int)

    class Bar(val param1: String, val param2: Int = 12)

    class Baz(var param1: String = "a", var param2: Optional<Int> = Optional.empty())

    class TwoConstructorsWithDefaultOne {
        // kotlin 의 primary constructor가 아닙니다.
        constructor()

        constructor(param1: String)
    }

    class TwoConstructorsWithoutDefaultOne {
        // kotlin 의 primary constructor가 아닙니다.
        constructor(param1: String)

        constructor(param1: String, param2: String)
    }

    class OneConstructorWithDefaultOne {
        // kotlin 의 primary constructor가 아닙니다.
        constructor()
    }

    class OneConstructorWithoutDefaultOne {
        // kotlin 의 primary constructor가 아닙니다.
        constructor(param1: String)
    }
}