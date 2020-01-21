package io.kommons.designpatterns.factory.method.weapon

/**
 * ElfWeapon
 *
 * @author debop
 */
class ElfWeapon(override val weaponType: WeaponType): Weapon {

    override fun toString(): String = "Elven $weaponType"

}