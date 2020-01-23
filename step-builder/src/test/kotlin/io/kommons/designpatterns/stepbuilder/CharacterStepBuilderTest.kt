package io.kommons.designpatterns.stepbuilder

import io.kommons.logging.KLogging
import io.kommons.logging.info
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldHaveSize
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test

class CharacterStepBuilderTest {

    companion object: KLogging()

    @Test
    fun `build wizard`() {
        val character = CharacterStepBuilder.newBuilder()
            .name("Merlin")
            .wizardClass("alchemist")
            .withSpell("poison")
            .withAbility("invisibility")
            .withAbility("wisdom")
            .noMoreAbilities()
            .build()

        log.info { "Wizard: $character" }

        with(character) {
            name shouldEqual "Merlin"
            wizardClass shouldEqual "alchemist"
            spell shouldEqual "poison"
            toString().shouldNotBeEmpty()

            abilities.shouldNotBeNull()
            abilities!! shouldHaveSize 2
            abilities!! shouldContainSame listOf("invisibility", "wisdom")
        }
    }

    @Test
    fun `build poor wizard`() {
        val character = CharacterStepBuilder.newBuilder()
            .name("Merlin")
            .wizardClass("alchemist")
            .noSpell()
            .build()

        log.info { "Poor wizard: $character" }

        with(character) {
            name shouldEqual "Merlin"
            wizardClass shouldEqual "alchemist"
            spell.shouldBeNull()
            abilities.shouldBeNull()
            toString().shouldNotBeEmpty()
        }
    }

    @Test
    fun `build weak wizard`() {
        val character = CharacterStepBuilder.newBuilder()
            .name("Merlin")
            .wizardClass("alchemist")
            .withSpell("poison")
            .noAbilities()
            .build()

        log.info { "Weak wizard: $character" }

        with(character) {
            name shouldEqual "Merlin"
            wizardClass shouldEqual "alchemist"
            spell shouldEqual "poison"
            abilities.shouldBeNull()
            toString().shouldNotBeEmpty()
        }
    }

    @Test
    fun `build worrior`() {
        val character = CharacterStepBuilder.newBuilder()
            .name("Cuauhtemoc")
            .fighterClass("aztec")
            .withWeapon("spear")
            .withAbility("speed")
            .withAbility("strength")
            .noMoreAbilities()
            .build()

        log.info { "Worrior: $character" }

        with(character) {
            name shouldEqual "Cuauhtemoc"
            fighterClass shouldEqual "aztec"
            wizardClass.shouldBeNull()

            toString().shouldNotBeEmpty()

            abilities.shouldNotBeNull()
            abilities!! shouldHaveSize 2
            abilities!! shouldContainSame listOf("speed", "strength")
        }
    }

    @Test
    fun `build poor worrior`() {
        val character = CharacterStepBuilder.newBuilder()
            .name("Poor worrior")
            .fighterClass("none")
            .noWeapon()
            .build()

        log.info { "Poor worrior: $character" }

        with(character) {
            name shouldEqual "Poor worrior"
            fighterClass shouldEqual "none"
            wizardClass.shouldBeNull()
            abilities.shouldBeNull()
            toString().shouldNotBeEmpty()
        }
    }

    @Test
    fun `build weak worrior`() {
        val character = CharacterStepBuilder.newBuilder()
            .name("Weak worrior")
            .fighterClass("none")
            .withWeapon("Slingshot")
            .noAbilities()
            .build()

        log.info { "Weak worrior: $character" }

        with(character) {
            name shouldEqual "Weak worrior"
            fighterClass shouldEqual "none"
            weapon shouldEqual "Slingshot"
            wizardClass.shouldBeNull()
            abilities.shouldBeNull()
            toString().shouldNotBeEmpty()
        }
    }
}