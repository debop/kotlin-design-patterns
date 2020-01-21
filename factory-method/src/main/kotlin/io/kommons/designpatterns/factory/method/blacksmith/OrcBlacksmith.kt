package io.kommons.designpatterns.factory.method.blacksmith

import io.kommons.designpatterns.factory.method.weapon.OrcWeapon
import io.kommons.designpatterns.factory.method.weapon.Weapon
import io.kommons.designpatterns.factory.method.weapon.WeaponType

/**
 * OrcBlacksmith
 *
 * @author debop
 */
class OrcBlacksmith: Blacksmith {

    companion object {
        private val ORCASENAL = HashMap<WeaponType, OrcWeapon>()
    }

    override fun manufactureWeapon(weaponType: WeaponType): Weapon {
        return ORCASENAL.computeIfAbsent(weaponType) { type -> OrcWeapon(type) }
    }

}