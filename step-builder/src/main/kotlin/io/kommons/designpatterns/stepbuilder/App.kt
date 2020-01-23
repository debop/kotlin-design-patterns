package io.kommons.designpatterns.stepbuilder

class App

fun main() {

    val warrier = CharacterStepBuilder.newBuilder()
        .name("Amberjill")
        .fighterClass("Paladin")
        .withWeapon("Sword")
        .noAbilities()
        .build()

    println(warrier)

    val mage = CharacterStepBuilder.newBuilder()
        .name("Riobard")
        .wizardClass("Sorcerer")
        .withSpell("Fireball")
        .withAbility("Fire Aura")
        .withAbility("Teleport")
        .noMoreAbilities()
        .build()

    println(mage)

    val thief = CharacterStepBuilder.newBuilder()
        .name("Desmond")
        .fighterClass("Rogue")
        .noWeapon()
        .build()

    println(thief)
}