package io.kommons.designpatterns.builder

import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test

class HeroTest {

    @Test
    fun `build Hero by Builder`() {
        val heroName = "Sir Lancelot"

        val hero = Hero.Builder(Profession.WARRIOR, heroName)
            .withArmor(Armor.CHAIN_MAIL)
            .withWeapon(Weapon.SWORD)
            .withHairType(HairType.LONG_CURLY)
            .withHairColor(HairColor.BLOND)
            .build()

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