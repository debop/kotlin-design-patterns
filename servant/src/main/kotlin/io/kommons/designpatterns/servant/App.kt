package io.kommons.designpatterns.servant

import io.kommons.logging.KotlinLogging
import io.kommons.logging.info

class App

private val log = KotlinLogging.logger { }

fun main() {

    val jenkins = Servant("Jenkins")
    val travis = Servant("Travis")

    scenario(jenkins, 1)
    scenario(travis, 0)

}

fun scenario(servant: Servant, compliment: Int) {

    val k = King()
    val q = Queen()

    val guests = listOf(k, q)

    // feed
    servant.feed(k)
    servant.feed(q)

    // serve drinks
    servant.giveWine(k)
    servant.giveWine(q)

    // compliment
    servant.giveCompliments(guests[compliment])

    guests.forEach { it.changeMood() }

    // check your luck
    if (servant.checkIfYouWillBeHanged(guests)) {
        log.info { "${servant.name} will live another day" }
    } else {
        log.info { "Poor ${servant.name}. His days are numbered." }
    }
}