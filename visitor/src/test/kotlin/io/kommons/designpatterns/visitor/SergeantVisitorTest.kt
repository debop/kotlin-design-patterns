package io.kommons.designpatterns.visitor

/**
 * SergeantVisitorTest
 *
 * @author debop
 * @since 29/09/2019
 */
class SergeantVisitorTest: AbstractVisitorTest<SergeantVisitor>(
    visitor = SergeantVisitor(),
    sergeantResponse = "Hello sergeant"
)