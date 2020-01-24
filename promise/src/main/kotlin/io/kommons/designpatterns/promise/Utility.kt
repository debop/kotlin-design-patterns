package io.kommons.designpatterns.promise

import io.kommons.logging.KotlinLogging
import io.kommons.logging.error
import io.kommons.logging.info
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.Writer
import java.net.URL
import kotlin.streams.asSequence

private val log = KotlinLogging.logger { }

fun characterFrequency(fileLocation: String): Map<Char, Long> {
    return try {
        FileReader(fileLocation).buffered().use { reader ->
            reader.lines()
                .asSequence<String>()
                .flatMap { it.chars().asSequence().map { it.toChar() } }
                .groupBy { it }
                .map { it.key to it.value.size.toLong() }
                .toMap()
        }
    } catch (e: IOException) {
        log.error(e) { "Fail to read file. fileLocation=$fileLocation" }
        emptyMap()
    }
}


fun Map<Char, Long>.lowestFrequencyChar(): Char =
    this.entries.minBy { it.value }?.key ?: throw NoSuchElementException()

fun countLines(fileLocation: String): Int {
    return try {
        FileReader(fileLocation).buffered().use {
            it.lines().count().toInt()
        }
    } catch (e: IOException) {
        log.error(e) { "Fail to read file. fileLocation=$fileLocation" }
        0
    }
}

fun URL.downloadFile(): String {
    log.info { "Downloading contents from url: $this" }

    val file = File.createTempFile("promise_pattern", null)

    InputStreamReader(this.openStream()).buffered().use { reader ->
        FileWriter(file).use { writer ->
            do {
                val line = reader.readLine()
                line?.let { writer.writeln(it) }
            } while (line != null)
        }
    }
    log.info { "File downloaded at ${file.absolutePath}" }
    return file.absolutePath
}

private val LINE_SEPARATOR = System.getProperty("line.separator")

internal fun Writer.writeln(msg: String) {
    write(msg)
    write(LINE_SEPARATOR)
}