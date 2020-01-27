package io.kommons.designpatterns.specification

import io.kommons.designpatterns.specification.Color.DARK
import io.kommons.designpatterns.specification.Color.GREEN
import io.kommons.designpatterns.specification.Color.LIGHT
import io.kommons.designpatterns.specification.Color.RED
import io.kommons.designpatterns.specification.Movement.FLYING
import io.kommons.designpatterns.specification.Movement.SWIMMING
import io.kommons.designpatterns.specification.Movement.WALKING
import io.kommons.designpatterns.specification.Size.LARGE
import io.kommons.designpatterns.specification.Size.NORMAL
import io.kommons.designpatterns.specification.Size.SMALL
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class CreatureTest {

    companion object: KLogging()

    private fun dataProvider(): Stream<Arguments> =
        Stream.of(
            Arguments.of(Dragon(), "Dragon", LARGE, FLYING, RED, Mass(39300.0)),
            Arguments.of(Goblin(), "Goblin", SMALL, WALKING, GREEN, Mass(30.0)),
            Arguments.of(KillerBee(), "KillerBee", SMALL, FLYING, LIGHT, Mass(6.7)),
            Arguments.of(Octopus(), "Octopus", NORMAL, SWIMMING, DARK, Mass(12.0)),
            Arguments.of(Shark(), "Shark", NORMAL, SWIMMING, LIGHT, Mass(500.0)),
            Arguments.of(Troll(), "Troll", LARGE, WALKING, DARK, Mass(4000.0))
        )

    @ParameterizedTest
    @MethodSource("dataProvider")
    fun `get default properties of creature`(testedCreature: Creature,
                                             name: String,
                                             size: Size,
                                             movement: Movement,
                                             color: Color,
                                             mass: Mass) {
        testedCreature.name shouldEqual name
        testedCreature.size shouldEqual size
        testedCreature.movement shouldEqual movement
        testedCreature.color shouldEqual color
        testedCreature.mass shouldEqual mass
    }
}