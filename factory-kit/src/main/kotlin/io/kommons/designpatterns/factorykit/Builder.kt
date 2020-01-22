package io.kommons.designpatterns.factorykit

interface Builder {

    fun add(name: WeaponType, supplier: () -> Weapon)

}

fun builderOf(adder: (name: WeaponType, supplier: () -> Weapon) -> Unit): Builder {
    return object: Builder {
        override fun add(name: WeaponType, supplier: () -> Weapon) {
            adder.invoke(name, supplier)
        }
    }
}