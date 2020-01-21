package io.kommons.designpatterns.singleton

/**
 * Singleton class. Eagerly initialized static instance guarantees thread safety.
 */
class IvoryTower private constructor() {

    companion object {

        private val INSTANCE: IvoryTower = IvoryTower()

        @JvmStatic
        fun getInstance(): IvoryTower = INSTANCE
    }
}