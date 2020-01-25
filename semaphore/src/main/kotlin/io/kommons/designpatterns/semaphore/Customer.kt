package io.kommons.designpatterns.semaphore

import io.kommons.logging.KLogging
import io.kommons.logging.info

class Customer(name: String,
               private val fruitShop: FruitShop): Thread(name) {

    companion object: KLogging()

    private val fruitBowl: FruitBowl = FruitBowl()

    /**
     * The Customer repeatedly takes Fruit from the FruitShop until no Fruit remains.
     */
    override fun run() {
        while (fruitShop.countFruit() > 0) {
            fruitShop.takeBowl()?.let { bowl ->
                bowl.take()?.let { fruit ->
                    log.info { "$name took $fruit" }
                    fruitBowl.put(fruit)
                    fruitShop.returnBowl(bowl)
                }
            }
        }

        log.info { "$name took $fruitBowl" }
    }
}