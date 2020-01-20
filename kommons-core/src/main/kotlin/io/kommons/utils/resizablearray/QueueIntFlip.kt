package io.kommons.utils.resizablearray

import io.kommons.logging.KLogging

/**
 * Same as QueueFillCount, except that QueueFlip uses a flip flag to keep track of when the internal writePos has
 * "overflowed" (meaning it goes back to 0). Other than that, the two implementations are very similar in functionality.
 *
 * One additional difference is that QueueFlip has an available() method, where this is a public variable in
 * QueueFillCount.
 */
class QueueIntFlip(val capacity: Int) {

    companion object: KLogging()

    // TODO: 이 것을 OnHeap, OffHeap 으로 나누면 OffHeapMemory 로 확장할 수 있다.
    private val elements: IntArray = IntArray(capacity)

    private var writePos: Int = 0
    private var readPos: Int = 0
    private var flipped: Boolean = false

    fun reset() {
        writePos = 0
        readPos = 0
        flipped = false
    }

    fun available(): Int = when {
        flipped -> capacity - readPos + writePos
        else    -> writePos - readPos
    }

    fun remainingCapacity(): Int = when {
        flipped -> readPos - writePos
        else    -> capacity - writePos
    }

    fun put(element: Int): Boolean {
        fun putElement(): Boolean {
            elements[writePos++] = element
            return true
        }

        return if (!flipped) {
            if (writePos == capacity) {
                writePos = 0
                flipped = true
                if (writePos < readPos) putElement() else false
            } else {
                putElement()
            }
        } else {
            if (writePos < readPos) putElement() else false
        }
    }

    fun put(newElements: IntArray, length: Int): Int {
        var newElementReadPos = 0
        if (!flipped) {
            // readPos lower than writePos - free sections are:
            // 1) from writePos to capacity
            // 2) from 0 to readPos
            if (length <= capacity - writePos) {
                while (newElementReadPos < length) {
                    elements[writePos++] = newElements[newElementReadPos++]
                }
            } else {
                // new elements must be divided between top and bottom of elements array

                // writing to top
                while (writePos < capacity) {
                    elements[writePos++] = newElements[newElementReadPos++]
                }
                // writing to bottom
                writePos = 0
                flipped = true
                val endPos = Math.min(readPos, length - newElementReadPos)
                while (writePos < endPos) {
                    elements[writePos++] = newElements[newElementReadPos++]
                }
            }
        } else {
            // readPos higher than writePos -- free sections are:
            // 1) from writePos to readPos
            val endPos = Math.min(readPos, writePos + length)
            while (writePos < endPos) {
                elements[writePos++] = newElements[newElementReadPos++]
            }
        }
        return newElementReadPos
    }

    fun take(): Int {
        return if (!flipped) {
            if (readPos < writePos) elements[readPos++] else -1
        } else {
            if (readPos == capacity) {
                readPos = 0
                flipped = false
                if (readPos < writePos) elements[readPos++] else -1
            } else {
                elements[readPos++]
            }
        }
    }

    fun take(into: IntArray, length: Int): Int {
        var intoWritePos = 0

        if (!flipped) {
            // writePos higher than readPos - available section is writePos - readPos

            val endPos = Math.min(writePos, readPos + length)
            while (readPos < endPos) {
                into[intoWritePos++] = elements[readPos++]
            }
        } else {
            // readPos higher than writePos - available section are top + bottom of elements array
            if (length <= capacity - readPos) {
                while (intoWritePos < length) {
                    into[intoWritePos++] = elements[readPos++]
                }
            } else {
                //length is higher than elements available at the top of the elements array
                //split copy into a copy from both top and bottom of elements array.

                // copy from top
                while (readPos < capacity) {
                    into[intoWritePos++] = elements[readPos++]
                }

                // copy from bottom
                readPos = 0
                flipped = false
                val endPos = Math.min(writePos, length - intoWritePos)
                while (readPos < endPos) {
                    into[intoWritePos++] = elements[readPos++]
                }
            }
        }

        return intoWritePos
    }

}