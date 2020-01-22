package io.kommons.designpatterns.factorykit

enum class WeaponType {
    SWORD, AXE, BOW, SPEAR
}

interface Weapon

class Sword: Weapon {
    override fun toString(): String = "Sword"
}

class Axe: Weapon {
    override fun toString(): String = "Axe"
}

class Bow: Weapon {
    override fun toString(): String = "Bow"
}

class Spear: Weapon {
    override fun toString(): String = "Spear"
}




