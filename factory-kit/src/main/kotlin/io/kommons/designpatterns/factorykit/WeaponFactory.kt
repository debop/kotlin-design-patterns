package io.kommons.designpatterns.factorykit

import io.kommons.logging.KLogging
import io.kommons.logging.info

interface WeaponFactory {

    companion object: KLogging() {
        @JvmStatic
        fun factory(consumer: (Builder) -> Unit): WeaponFactory {
            val map = hashMapOf<WeaponType, () -> Weapon>()

            val builder: Builder = builderOf { name, supplier -> map[name] = supplier }
            consumer.invoke(builder)

            return weaponFactoryOf {
                log.info { "Create $it weapon." }
                map[it]!!.invoke()
            }
        }
    }

    fun create(name: WeaponType): Weapon

}

fun weaponFactoryOf(creator: (WeaponType) -> Weapon): WeaponFactory {
    return object: WeaponFactory {
        override fun create(name: WeaponType): Weapon = creator.invoke(name)
    }
}