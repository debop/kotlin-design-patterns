package io.kommons.designpatterns.visitor

/**
 * CommanderVisitorTest
 *
 * @author debop
 * @since 29/09/2019
 */
class CommanderVisitorTest: AbstractVisitorTest<CommanderVisitor>(
    visitor = CommanderVisitor(),
    commanderResponse = "Good to see you commander"
)