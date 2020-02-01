package io.kommons.designpatterns.interpreter

import io.kommons.logging.KotlinLogging
import io.kommons.logging.info
import java.util.Stack

/**
 * The Interpreter pattern is a design pattern that specifies how to evaluate sentences in a
 * language. The basic idea is to have a class for each symbol (terminal or nonterminal) in a
 * specialized computer language. The syntax tree of a sentence in the language is an instance of
 * the composite pattern and is used to evaluate (interpret) the sentence for a client.
 *
 * <p>In this example we use the Interpreter pattern to break sentences into expressions ([Expression])
 * that can be evaluated and as a whole form the result.
 */
class App

private val log = KotlinLogging.logger { }

fun main() {
    val tokenString = "4 3 2 - 1 + *"
    val stack = Stack<Expression>()

    val tokens = tokenString.split(" ")
    tokens.forEach { s ->
        if (isOperator(s)) {
            val rightExpr = stack.pop()
            val leftExpr = stack.pop()

            log.info { "poped from stack left=${leftExpr.interpret()}, right=${rightExpr.interpret()}" }

            val operator = getOperatorInstance(s, leftExpr, rightExpr)
            log.info { "operator: $operator" }

            val result = operator.interpret()
            val resultExpr = NumberExpression(result)
            stack.push(resultExpr)
            log.info { "push result to stack: ${resultExpr.interpret()}" }
        } else {
            val i = NumberExpression(s)
            stack.push(i)
            log.info { "push to stack: ${i.interpret()}" }
        }
    }
}

private fun isOperator(s: String): Boolean = s in "+-*"

fun getOperatorInstance(s: String, left: Expression, right: Expression): Expression = when (s) {
    "+"  -> PlusExpression(left, right)
    "-"  -> MinusExpression(left, right)
    "*"  -> MultiplyExpression(left, right)
    else -> MultiplyExpression(left, right)
}
