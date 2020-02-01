package io.kommons.designpatterns.interpreter

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(Lifecycle.PER_CLASS)
abstract class ExpressionTest<E: Expression>(private val expectedToString: String,
                                             private val factory: (NumberExpression, NumberExpression) -> E) {

    companion object: KLogging() {
        fun prepareParameters(resultCalc: (Int, Int) -> Int): Stream<Arguments> {
            val testData = mutableListOf<Arguments>()

            for (i in -10 until 10) {
                for (j in -10 until 10) {
                    testData += Arguments.of(NumberExpression(i), NumberExpression(j), resultCalc(i, j))
                }
            }
            return testData.stream()
        }
    }

    abstract fun expressionProvider(): Stream<Arguments>

    @ParameterizedTest
    @MethodSource("expressionProvider")
    fun testInterpret(first: NumberExpression, second: NumberExpression, result: Int) {
        val expr = factory(first, second)
        expr.shouldNotBeNull()
        expr.interpret() shouldEqualTo result
    }

    @ParameterizedTest
    @MethodSource("expressionProvider")
    fun testToString(first: NumberExpression, second: NumberExpression) {
        val expr = factory(first, second)
        log.debug { "expr=$expr" }
        expr.shouldNotBeNull()
        expr.toString() shouldEqual expectedToString
    }
}