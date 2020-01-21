package io.kommons.designpatterns.factory.method

import io.kommons.designpatterns.factory.method.blacksmith.ElfBlacksmith
import io.kommons.designpatterns.factory.method.blacksmith.OrcBlacksmith
import io.kommons.designpatterns.factory.method.weapon.ElfWeapon
import io.kommons.designpatterns.factory.method.weapon.OrcWeapon
import io.kommons.designpatterns.factory.method.weapon.WeaponType
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class FactoryMethodTest {

    @ParameterizedTest
    @EnumSource(WeaponType::class)
    fun `orc blacksmith with spear`(weaponType: WeaponType) {
        val blacksmith = OrcBlacksmith()
        val weapon = blacksmith.manufactureWeapon(weaponType)

        weapon.weaponType shouldEqual weaponType
        weapon shouldBeInstanceOf OrcWeapon::class
    }

    @ParameterizedTest
    @EnumSource(WeaponType::class)
    fun `elf blacksmith with spear`(weaponType: WeaponType) {
        val blacksmith = ElfBlacksmith()
        val weapon = blacksmith.manufactureWeapon(weaponType)

        weapon.weaponType shouldEqual weaponType
        weapon shouldBeInstanceOf ElfWeapon::class
    }
}