package io.kommons.designpatterns.dirty.flag

import io.kommons.logging.KLogging
import io.kommons.logging.info
import java.io.File
import java.nio.file.Files
import java.util.ArrayList

class DataFetcher {

    companion object: KLogging()

    val filename = "/world.txt"
    var lastFetched: Long = -1

    private fun isDirty(fileLastModified: Long): Boolean {
        if (lastFetched != fileLastModified) {
            lastFetched = fileLastModified
            return true
        }

        return false
    }

    fun fetch(): List<String> {
        val file = File(this.javaClass.getResource(filename)!!.file)
        val lastModifiedTime = Files.getLastModifiedTime(file.toPath())
        return if (isDirty(lastModifiedTime.toMillis())) {
            log.info { "$filename is dirty! Re-fetching file content..." }
            val data = ArrayList<String>()
            file.readLines().forEach { data.add(it) }
            data
        } else {
            emptyList()
        }
    }
}