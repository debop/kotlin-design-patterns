package io.kommons.designpatterns.visitor

/**
 * SoldierVisitorTest
 *
 * @author debop
 * @since 29/09/2019
 */
class SoldierVisitorTest
    : AbstractVisitorTest<SoldierVisitor>(visitor = SoldierVisitor(),
                                          soldierResponse = "Greetings soldier")