package io.kommons.designpatterns.interpreter

import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class NumberExpressionTest: ExpressionTest<NumberExpression>("number", { f, s -> f }) {

    override fun expressionProvider(): Stream<Arguments> =
        prepareParameters { f, s -> f }

    @ParameterizedTest
    @MethodSource("expressionProvider")
    fun testFromString(first: NumberExpression) {
        val expectedValue = first.interpret()
        val testStringValue = expectedValue.toString()
        val numberExpr = NumberExpression(testStringValue)

        numberExpr.interpret() shouldEqualTo expectedValue
    }
}