package io.kommons.designpatterns.factorykit

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

class FactoryKitTest {

    companion object: KLogging()

    private lateinit var factory: WeaponFactory

    @BeforeEach
    fun init() {
        factory = WeaponFactory.factory { builder ->
            builder.add(WeaponType.SPEAR, ::Spear)
            builder.add(WeaponType.AXE, ::Axe)
            builder.add(WeaponType.SWORD, ::Sword)
        }
    }

    @Test
    fun `create Spear weapon`() {
        val weapon = factory.create(WeaponType.SPEAR)
        verifyWeapon(weapon, Spear::class)
    }

    @Test
    fun `create Axe weapon`() {
        val weapon = factory.create(WeaponType.AXE)
        verifyWeapon(weapon, Axe::class)
    }

    @Test
    fun `create Sword weapon`() {
        val weapon = factory.create(WeaponType.SWORD)
        verifyWeapon(weapon, Sword::class)
    }

    private fun verifyWeapon(weapon: Weapon, kclass: KClass<*>) {
        kclass.isInstance(weapon).shouldBeTrue()
    }
}