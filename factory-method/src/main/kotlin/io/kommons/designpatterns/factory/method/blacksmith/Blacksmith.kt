package io.kommons.designpatterns.factory.method.blacksmith

import io.kommons.designpatterns.factory.method.weapon.Weapon
import io.kommons.designpatterns.factory.method.weapon.WeaponType

/**
 * Blacksmith
 *
 * @author debop
 */
interface Blacksmith {

    fun manufactureWeapon(weaponType: WeaponType): Weapon
}