package io.kommons.utils.resizablearray

import io.kommons.ToStringBuilder
import io.kommons.logging.KLogging
import io.kommons.logging.debug
import java.io.Serializable
import java.nio.ByteBuffer

/**
 * ResizableArray
 *
 * @author debop
 */
class ResizableArray(private val arrayBuffer: ResizableArrayBuffer): Serializable {

    companion object: KLogging()

    lateinit var sharedArray: ByteArray

    var offset = 0 //offset into sharedArray where this message data starts.
    var capacity = 0 //the size of the section in the sharedArray allocated to this message.
    var length = 0 //the number of bytes used of the allocated section.

    /**
     * Writes data from the ByteBuffer into this message - meaning into the buffer backing this message.
     *
     * @param buffer The ByteBuffer containing the message data to write.
     * @return write byte size
     */
    fun writeToMessage(buffer: ByteBuffer): Int {
        val remaining = buffer.remaining()

        while (length + remaining > capacity) {
            // expand message.
            if (!arrayBuffer.expandArray(this)) {
                log.debug { "Can't expand array. return -1" }
                return -1
            }
        }

        val bytesToCopy = minOf(remaining, capacity - length)

        // bytebuffer 내용을 읽어 sharedArray에 복사합니다.
        log.debug { "Write to sharedArray... offset=$offset, capacity=$capacity, length=$length" }
        buffer.get(sharedArray, offset + length, bytesToCopy)

        length += bytesToCopy
        return bytesToCopy
    }

    fun free() {
        log.debug { "Free array ..." }
        arrayBuffer.free(this)
    }

    override fun toString(): String {
        return ToStringBuilder(this)
            .add("offset", offset)
            .add("length", length)
            .add("capacity", capacity)
            .toString()
    }
}