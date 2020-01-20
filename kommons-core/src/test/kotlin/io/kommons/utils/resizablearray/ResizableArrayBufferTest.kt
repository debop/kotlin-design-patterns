package io.kommons.utils.resizablearray

import io.kommons.utils.resizablearray.ResizableArrayBuffer.Companion.KB
import io.kommons.utils.resizablearray.ResizableArrayBuffer.Companion.MB
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test

class ResizableArrayBufferTest {

    @Test
    fun `get array`() {
        val smallBlock = ArrayBlock(4 * KB, 2)
        val mediumBlock = ArrayBlock(128 * KB, 32)
        val largeBlock = ArrayBlock(MB, 4)

        val buffer = ResizableArrayBuffer(smallBlock, mediumBlock, largeBlock)
        val array = buffer.getArray()!!

        array.shouldNotBeNull()
        array.offset shouldEqualTo 0
        array.length shouldEqualTo 0
        array.capacity shouldEqualTo 4 * KB

        val array2 = buffer.getArray()!!

        with(array2) {
            offset shouldEqualTo 4 * KB
            length shouldEqualTo 0
            capacity shouldEqualTo 4 * KB
        }

        buffer.getArray().shouldBeNull()
    }

    @Test
    fun `expand array`() {
        val smallBlock = ArrayBlock(4 * KB, 10)
        val mediumBlock = ArrayBlock(128 * KB, 10)
        val largeBlock = ArrayBlock(MB, 1)

        val buffer = ResizableArrayBuffer(smallBlock, mediumBlock, largeBlock)

        val array = buffer.getArray()!!

        with(array) {
            offset shouldEqualTo 0
            length shouldEqualTo 0
            capacity shouldEqualTo 4 * KB
        }

        buffer.smallFreeBlocks.available() shouldEqualTo 9
        buffer.mediumFreeBlocks.available() shouldEqualTo 10
        buffer.largeFreeBlocks.available() shouldEqualTo 1

        buffer.expandArray(array)

        with(array) {
            offset shouldEqualTo smallBlock.capacity
            length shouldEqualTo 0
            capacity shouldEqualTo 128 * KB
        }

        buffer.smallFreeBlocks.available() shouldEqualTo 10
        buffer.mediumFreeBlocks.available() shouldEqualTo 9
        buffer.largeFreeBlocks.available() shouldEqualTo 1

        buffer.expandArray(array)

        with(array) {
            offset shouldEqualTo smallBlock.capacity + mediumBlock.capacity
            length shouldEqualTo 0
            capacity shouldEqualTo MB
        }

        buffer.smallFreeBlocks.available() shouldEqualTo 10
        buffer.mediumFreeBlocks.available() shouldEqualTo 10
        buffer.largeFreeBlocks.available() shouldEqualTo 0


        //
        // next expansion should not be possible
        //
        buffer.expandArray(array).shouldBeFalse()

        with(array) {
            offset shouldEqualTo smallBlock.capacity + mediumBlock.capacity
            length shouldEqualTo 0
            capacity shouldEqualTo MB
        }

        buffer.smallFreeBlocks.available() shouldEqualTo 10
        buffer.mediumFreeBlocks.available() shouldEqualTo 10
        buffer.largeFreeBlocks.available() shouldEqualTo 0


        array.free()

        buffer.smallFreeBlocks.available() shouldEqualTo 10
        buffer.mediumFreeBlocks.available() shouldEqualTo 10
        buffer.largeFreeBlocks.available() shouldEqualTo 1

    }
}