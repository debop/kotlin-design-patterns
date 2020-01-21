package io.kommons.designpatterns.factory.method.weapon

/**
 * OrcWeapon
 *
 * @author debop
 */
class OrcWeapon(override val weaponType: WeaponType): Weapon {

    override fun toString(): String = "Orc $weaponType"

}