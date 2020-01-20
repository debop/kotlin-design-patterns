package io.kommons.junit.jupiter.output

import io.kommons.logging.KLogging
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream

/**
 * 테스트 시에 Console에 출력되는 정보를 보관한다
 *
 * @author debop
 */
class OutputCapture {

    companion object: KLogging()

    private var copy: ByteArrayOutputStream? = null
    private var captureOut: CaptureOutputStream? = null
    private var captureErr: CaptureOutputStream? = null

    inline fun expect(body: (String) -> Unit) {
        body(capture())
    }

    fun capture(): String {
        flush()
        return copy?.toString(Charsets.UTF_8.name()) ?: ""
    }

    internal fun startCapture() {
        copy = ByteArrayOutputStream()
        captureOut = CaptureOutputStream(System.out, copy!!)
        captureErr = CaptureOutputStream(System.err, copy!!)

        System.setOut(PrintStream(captureOut!!))
        System.setErr(PrintStream(captureErr!!))
    }

    internal fun finishCapture() {
        System.setOut(this.captureOut?.original)
        System.setErr(this.captureErr?.original)
        this.copy?.close()
        this.captureOut?.close()
        this.captureErr?.close()
    }

    private fun flush() {
        captureOut?.run { runCatching { this.flush() } }
        captureErr?.run { runCatching { this.flush() } }
    }

    override fun toString(): String {
        flush()
        return capture()
    }

    private class CaptureOutputStream(val original: PrintStream, val copy: OutputStream): OutputStream() {

        override fun write(b: Int) {
            copy.write(b)
            original.write(b)
            original.flush()
        }

        override fun write(bytes: ByteArray) {
            write(bytes, 0, bytes.size)
        }

        override fun write(bytes: ByteArray, off: Int, len: Int) {
            copy.write(bytes, off, len)
            original.write(bytes, off, len)
            original.flush()
        }

        override fun flush() {
            copy.flush()
            original.flush()
        }

        override fun close() {
            copy.close()
            original.close()
        }
    }
}