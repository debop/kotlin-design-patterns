package io.kommons.designpatterns.factory.method.blacksmith

import io.kommons.designpatterns.factory.method.weapon.ElfWeapon
import io.kommons.designpatterns.factory.method.weapon.Weapon
import io.kommons.designpatterns.factory.method.weapon.WeaponType

/**
 * ElfBlacksmith
 *
 * @author debop
 */
class ElfBlacksmith: Blacksmith {

    companion object {
        private val ELFARSENAL = HashMap<WeaponType, Weapon>()
    }

    override fun manufactureWeapon(weaponType: WeaponType): Weapon {
        return ELFARSENAL.computeIfAbsent(weaponType) { type -> ElfWeapon(type) }
    }
}