package io.kommons.designpatterns.templatemethod

/**
 * SubtleMethodTest
 *
 * @author debop
 */
class SubtleMethodTest: StealingMethodTest<SubtleMethod>(
    SubtleMethod(),
    "shop keeper",
    "The target has been chosen as shop keeper.",
    "Approach the shop keeper with tears running and hug him!",
    "While in close contact grab the shop keeper's wallet."
)