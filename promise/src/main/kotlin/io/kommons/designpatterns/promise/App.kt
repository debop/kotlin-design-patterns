package io.kommons.designpatterns.promise

import io.kommons.logging.KotlinLogging
import io.kommons.logging.error
import io.kommons.logging.info
import java.net.URL
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class App

private val log = KotlinLogging.logger { }

private const val DEFAULT_URL = "https://raw.githubusercontent.com/debop/kotlin-design-patterns/master/promise/README.md"
private val executor = Executors.newFixedThreadPool(2)
private val stopLatch = CountDownLatch(2)

private fun promiseUsage() {
    calculateLineCount()
    calculateLowestFrequencyChar()
}

private fun calculateLineCount() {
    lowestFrequencyChar()
        .thenAccept { char ->
            log.info { "Char with lowest frequency is: $char" }
            taskCompleted()
        }
}

private fun calculateLowestFrequencyChar() {
    countLines()
        .thenAccept { count ->
            log.info { "Line count is $count" }
            taskCompleted()
        }
}

private fun lowestFrequencyChar(): Promise<Char> =
    characterFrequency().thenApply { map -> map?.lowestFrequencyChar() }

private fun characterFrequency(): Promise<Map<Char, Long>> =
    download(DEFAULT_URL).thenApply { lines -> characterFrequency(lines!!) }

private fun countLines(): Promise<Int> =
    download(DEFAULT_URL).thenApply { lines -> countLines(lines!!) }

private fun download(urlString: String): Promise<String> =
    Promise<String>()
        .fulfillInAsync(executor) { URL(urlString).downloadFile() }
        .onError { error ->
            log.error(error) { "Fail to download file." }
            taskCompleted()
        }

private fun stop() {
    stopLatch.await()
    executor.shutdownNow()
}

private fun taskCompleted() {
    stopLatch.countDown()
}

fun main() {
    try {
        promiseUsage()
    } finally {
        stop()
    }
}