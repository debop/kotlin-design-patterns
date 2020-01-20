package io.kommons.utils.resizablearray

import io.kommons.utils.resizablearray.ResizableArrayBuffer.Companion.KB
import io.kommons.utils.resizablearray.ResizableArrayBuffer.Companion.MB
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test
import java.nio.ByteBuffer

class ResizableArrayTest {

    @Test
    fun `write to message`() {

        val arrayBuffer = ResizableArrayBuffer(ArrayBlock(4 * KB, 10),
                                               ArrayBlock(128 * KB, 10),
                                               ArrayBlock(MB, 1))

        val array = arrayBuffer.getArray()
        array.shouldNotBeNull()

        val bytebuffer = ByteBuffer.allocate(MB)
        fill(bytebuffer, 4096)

        var written = array.writeToMessage(bytebuffer)
        written shouldEqualTo 4 * KB
        array.length shouldEqualTo 4 * KB

        fill(bytebuffer, 124 * KB)
        written = array.writeToMessage(bytebuffer)
        written shouldEqualTo 124 * KB
        array.length shouldEqualTo 128 * KB


        fill(bytebuffer, (KB - 128) * KB)
        written = array.writeToMessage(bytebuffer)

        written shouldEqualTo (KB - 128) * KB
        array.length shouldEqualTo MB


        // large 까지 expand 했기 때문에 더 이상 추가할 수 없다
        fill(bytebuffer, 1)
        written = array.writeToMessage(bytebuffer)
        written shouldEqualTo -1
    }

    private fun fill(bytebuffer: ByteBuffer, length: Int) {
        bytebuffer.clear()
        for (i in 0 until length) {
            bytebuffer.put((i % 128).toByte())
        }
        bytebuffer.flip()
    }
}