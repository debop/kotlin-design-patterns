package io.kommons.designpatterns.builder

import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test

class KotlinHeroTest {

    @Test
    fun `create Hero by Kotlin style`() {
        val heroName = "Sir Lancelot"
        val hero = KotlinHero(profession = Profession.WARRIOR,
                              name = heroName,
                              armor = Armor.CHAIN_MAIL,
                              weapon = Weapon.SWORD,
                              hairType = HairType.LONG_CURLY,
                              hairColor = HairColor.BLOND)

        hero.shouldNotBeNull()
        hero.toString().shouldNotBeEmpty()

        hero.profession shouldEqual Profession.WARRIOR
        hero.name shouldEqual heroName
        hero.armor shouldEqual Armor.CHAIN_MAIL
        hero.weapon shouldEqual Weapon.SWORD
        hero.hairType shouldEqual HairType.LONG_CURLY
        hero.hairColor shouldEqual HairColor.BLOND
    }
}