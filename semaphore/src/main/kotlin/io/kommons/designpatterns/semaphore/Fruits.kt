package io.kommons.designpatterns.semaphore

import io.kommons.designpatterns.semaphore.Fruit.FruitType
import io.kommons.logging.KLogging
import io.kommons.logging.error

class Fruit(val type: FruitType) {

    override fun toString(): String = type.name.capitalize()

    enum class FruitType {
        ORANGE, APPLE, LEMON
    }
}

class FruitBowl {

    private val fruits = arrayListOf<Fruit>()

    val countFruit: Int get() = fruits.size

    fun put(f: Fruit) {
        fruits.add(f)
    }

    fun take(): Fruit? = when {
        fruits.isNotEmpty() -> fruits.removeAt(0)
        else                -> null
    }

    override fun toString(): String {
        val maps = fruits.groupBy { it.type }
            .map { it.key.name to it.value.size }
            .toMap()

        return maps
            .map { (name, size) -> "$size $name" }
            .joinToString()
    }
}

class FruitShop {

    companion object: KLogging()

    private val bowls = Array(3) { FruitBowl() }
    private val availables = Array(3) { true }
    private val semaphore = Semaphore(3)

    init {
        repeat(100) {
            bowls[0].put(Fruit(FruitType.APPLE))
            bowls[1].put(Fruit(FruitType.ORANGE))
            bowls[2].put(Fruit(FruitType.LEMON))
        }
    }

    @Synchronized
    fun countFruit(): Int = bowls.map { it.countFruit }.sum()

    /**
     * Method called by Customer to get a FruitBowl from the shop. This method will try to acquire the
     * Semaphore before returning the first available FruitBowl.
     */
    @Synchronized
    fun takeBowl(): FruitBowl? {
        var bowl: FruitBowl? = null

        try {
            semaphore.acquire()

            val index = availables.indices.firstOrNull { availables[it] }
            if (index != null) {
                availables[index] = false
                bowl = bowls[index]
            }

        } catch (e: InterruptedException) {
            log.error(e) { "Fail to take bowl" }
        } finally {
            semaphore.release()
        }

        return bowl
    }

    /**
     * Method called by a Customer instance to return a FruitBowl to the shop. This method releases
     * the Semaphore, making the FruitBowl available to another Customer.
     */
    @Synchronized
    fun returnBowl(bowl: FruitBowl) {
        bowls.indices
            .firstOrNull { bowls[it] == bowl }
            ?.run { availables[this] = true }
    }
}