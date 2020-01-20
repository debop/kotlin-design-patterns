package io.kommons.examples

import io.kommons.examples.DelegateExamples.CloseableLazyVal
import io.kommons.junit.jupiter.output.CaptureSystemOutput
import io.kommons.junit.jupiter.output.OutputCapture
import org.amshove.kluent.shouldContain
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import java.io.Closeable
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Execution(ExecutionMode.SAME_THREAD)
@CaptureSystemOutput
class DelegateExamples {

    @Test
    fun `closeable property`(output: OutputCapture) {
        val foo = Foo()

        foo.access()

        Thread.sleep(100)

        with(output.toString()) {
            this shouldContain "Accessing"
            this shouldContain CloseableClass::class.java.name
        }

        foo.close()
        output.toString() shouldContain "Closing"
    }

    private class CloseableClass: Closeable {
        override fun close() {
            println("Closing ${this.javaClass.name}")
        }
    }

    class CloseableLazyVal<T: Closeable>(private val initializer: () -> T): ReadOnlyProperty<CloseableDelegateHost, T> {

        private val mValue: T? = null

        override fun getValue(thisRef: CloseableDelegateHost, property: KProperty<*>): T {
            var value = mValue
            if (value == null) {
                value = initializer()
                thisRef.registerCloseable(value)
            }
            return value
        }
    }

    interface CloseableDelegateHost: Closeable {
        fun registerCloseable(prop: Closeable)
    }

    private class CloseableDelegateHostImpl: CloseableDelegateHost {

        val closeables = arrayListOf<Closeable>()

        override fun registerCloseable(prop: Closeable) {
            closeables.add(prop)
        }

        override fun close() {
            closeables.forEach { it.close() }
        }
    }

    @Suppress("ktPropBy")
    private class Foo: CloseableDelegateHost by CloseableDelegateHostImpl() {

        private val stream by closeableLazy { CloseableClass() }

        fun access() {
            println("Accessing ${stream.javaClass.name}")
        }
    }
}

fun <T: Closeable> closeableLazy(initializer: () -> T) = CloseableLazyVal(initializer)

@Execution(ExecutionMode.SAME_THREAD)
@CaptureSystemOutput
class DelegatePropertiesExample {

    class Example {
        var p: String by Delegates.observable("") { prop, old, new ->
            println("property=${prop.name}, old=$old --> new=$new")
        }
    }

    interface Base {
        fun print()
    }

    class BaseImpl(val x: Int): Base {
        override fun print() {
            print(x)
        }
    }

    class Derived(b: Base): Base by b

    @Test
    fun `observable property`(output: OutputCapture) {
        val e = Example()

        e.p = "abc"
        Thread.sleep(100)
        output.toString() shouldContain "new=abc"

        e.p = "가나다"
        Thread.sleep(100)
        output.toString() shouldContain "new=가나다"
    }

    @Test
    fun `delegate class example`(output: OutputCapture) {
        val b = BaseImpl(10)
        Derived(b).print()

        output.toString() shouldContain "10"
    }
}
