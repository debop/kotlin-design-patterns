package io.kommons.designpatterns.tolerantreader

import io.kommons.AbstractValueObject

open class RainbowFish(val name: String,
                       val age: Int,
                       val lengthMeters: Int,
                       val weightTons: Int): AbstractValueObject() {

    override fun equalProperties(other: Any): Boolean {
        return other is RainbowFish &&
               name == other.name &&
               age == other.age &&
               lengthMeters == other.lengthMeters &&
               weightTons == other.weightTons
    }

    override fun toString(): String =
        "name=$name, age=$age, lengthMeters=$lengthMeters, $weightTons=$weightTons"
}

class RainbowFishV2 @JvmOverloads constructor(name: String, age: Int, lengthMeters: Int, weightTons: Int,
                                              val sleeping: Boolean = false,
                                              val hungry: Boolean = false,
                                              val angry: Boolean = false)
    : RainbowFish(name, age, lengthMeters, weightTons) {

    override fun equalProperties(other: Any): Boolean {
        return super.equalProperties(other) &&
               other is RainbowFishV2 &&
               sleeping == other.sleeping &&
               hungry == other.hungry &&
               angry == other.angry
    }

    override fun toString(): String =
        super.toString() + ", sleeping=$sleeping, hungry=$hungry, angry=$angry"
}