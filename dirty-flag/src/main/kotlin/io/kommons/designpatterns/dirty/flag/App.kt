package io.kommons.designpatterns.dirty.flag

import io.kommons.logging.KLogging
import io.kommons.logging.info
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class App {
    companion object: KLogging() {
        @JvmStatic
        fun main(vararg args: String) {
            val app = App()
            app.run()
        }
    }

    fun run() {
        val executorService = Executors.newSingleThreadScheduledExecutor()
        executorService.scheduleAtFixedRate(object: Runnable {
            val world = World()
            override fun run() {
                val countries = world.fetch()
                println("Our world currently has the following countries:-")
                log.info { "Our world currently has the following countries:-" }
                for (country in countries) {
                    println("\t" + country)
                    log.info("\t $country")
                }
            }
        }, 0, 10, TimeUnit.SECONDS)
    }
}