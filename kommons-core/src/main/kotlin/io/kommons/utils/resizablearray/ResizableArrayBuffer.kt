package io.kommons.utils.resizablearray

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import io.kommons.logging.trace

/**
 * ResizableArrayBuffer
 *
 * @author debop
 */
class ResizableArrayBuffer(private val smallBlock: ArrayBlock,
                           private val mediumBlock: ArrayBlock,
                           private val largeBlock: ArrayBlock) {

    companion object: KLogging() {

        const val KB: Int = 1024
        const val MB: Int = KB * KB

        private fun initFreeBlocks(freeBlocks: QueueIntFlip, startIndex: Int, arrayBlock: ArrayBlock) {
            for (i in startIndex until (startIndex + arrayBlock.capacity) step arrayBlock.size) {
                freeBlocks.put(i)
            }
        }
    }

    private val capacity = smallBlock.capacity + mediumBlock.capacity + largeBlock.capacity

    // TODO: sharedArray 를 OffHeapMemory 로 할당하면 OffHeap 으로 확장할 수 있다
    private val sharedArray: ByteArray = ByteArray(capacity)

    val smallFreeBlocks = QueueIntFlip(smallBlock.count)
    val mediumFreeBlocks = QueueIntFlip(mediumBlock.count)
    val largeFreeBlocks = QueueIntFlip(largeBlock.count)

    init {
        initFreeBlocks(smallFreeBlocks, 0, smallBlock)
        initFreeBlocks(mediumFreeBlocks, smallBlock.capacity, mediumBlock)
        initFreeBlocks(largeFreeBlocks, smallBlock.capacity + mediumBlock.capacity, largeBlock)
    }

    fun getArray(): ResizableArray? {
        val nextFreeSmallBlock = smallFreeBlocks.take()

        if (nextFreeSmallBlock == -1) {
            return null
        }
        return ResizableArray(this).also {
            it.sharedArray = sharedArray
            it.capacity = smallBlock.size
            it.offset = nextFreeSmallBlock
            it.length = 0
        }
    }

    fun expandArray(resizableArray: ResizableArray): Boolean = when (resizableArray.capacity) {
        smallBlock.size  -> moveArray(resizableArray, smallFreeBlocks, mediumFreeBlocks, mediumBlock.size)
        mediumBlock.size -> moveArray(resizableArray, mediumFreeBlocks, largeFreeBlocks, largeBlock.size)
        else             -> false
    }

    private fun moveArray(resizableArray: ResizableArray,
                          srcBlockQueue: QueueIntFlip,
                          destBlockQueue: QueueIntFlip,
                          newCapacity: Int): Boolean {
        val nextFreeBlock = destBlockQueue.take()
        if (nextFreeBlock == -1)
            return false

        System.arraycopy(sharedArray, resizableArray.offset, sharedArray, nextFreeBlock, resizableArray.length)

        srcBlockQueue.put(resizableArray.offset)  // free smaller block after copy

        resizableArray.sharedArray = this.sharedArray
        resizableArray.offset = nextFreeBlock
        resizableArray.capacity = newCapacity

        log.trace { "Move array. moved array: $resizableArray" }

        return true
    }

    fun free(resizableArray: ResizableArray) {
        log.debug { "Free resizable array..." }

        when (resizableArray.capacity) {
            smallBlock.size  -> smallFreeBlocks.put(resizableArray.offset)
            mediumBlock.size -> mediumFreeBlocks.put(resizableArray.offset)
            else             -> largeFreeBlocks.put(resizableArray.offset)
        }
    }
}