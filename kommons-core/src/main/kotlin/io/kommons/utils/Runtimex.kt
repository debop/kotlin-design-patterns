package io.kommons.utils

import io.kommons.logging.KLogging
import io.kommons.logging.info
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.Serializable
import java.net.URL
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

/**
 * [Runtime] 관련 정보
 *
 * @author debop
 */
object Runtimex: KLogging() {

    private val runtime = Runtime.getRuntime()

    @JvmField
    val availableProcessors = runtime.availableProcessors()

    @JvmStatic
    val availableMemory: Long
        get() = runtime.freeMemory() + (runtime.maxMemory() - runtime.totalMemory())

    @JvmStatic
    val availableMemoryPercent: Double
        get() = availableMemory.toDouble() * 100.0 / runtime.maxMemory()

    @JvmStatic
    val freeMemory: Long
        get() = runtime.freeMemory()

    @JvmStatic
    val freeMemoryPercent: Double
        get() = freeMemory.toDouble() / runtime.totalMemory()


    private const val TWO_GIGA = 2_000_000_000

    @JvmStatic
    fun compatMemory() {
        try {
            val unused = arrayListOf<ByteArray>()
            repeat(128) {
                unused.add(ByteArray(TWO_GIGA))
            }
        } catch (ignored: OutOfMemoryError) {
            // NOP
        }
        log.info { "Start Compat memory..." }
        System.gc()
    }

    @JvmStatic
    fun classLocation(clazz: Class<*>): URL =
        clazz.protectionDomain.codeSource.location

    /**
     * Registers a new virtual-machine shutdown hook.
     *
     * @param block VM 종료 시에 실행될 코드 블럭
     */
    @JvmStatic
    fun addShutdownHook(block: () -> Unit) {
        runtime.addShutdownHook(thread(start = false) { block() })
    }

    /** Process 실행 결과를 담은 Value Object */
    data class ProcessResult(val existCode: Int, val out: String): Serializable

    /**
     * Process 실행 시 결과를 [ProcessResult] 정보에 담아 반환합니다.
     */
    @JvmStatic
    fun run(process: Process): ProcessResult = ByteArrayOutputStream().use { bos ->

        val outputCapture = StreamGobbler(process.inputStream, bos, "out>")
        val errorCapture = StreamGobbler(process.errorStream, bos, "out>")

        outputCapture.start()
        errorCapture.start()

        val result = process.waitFor()

        outputCapture.waitFor()
        errorCapture.waitFor()

        ProcessResult(result, bos.toString(Charsets.UTF_8.name()))
    }

    /**
     * Consumes a stream
     */
    class StreamGobbler @JvmOverloads constructor(val input: InputStream,
                                                  val output: OutputStream? = null,
                                                  prefix: String? = null): Thread() {

        private val lock = ReentrantLock()
        private val condition = lock.newCondition()

        private var end = false

        private val prefixBytes: ByteArray = prefix?.toByteArray() ?: ByteArray(0)
        private val newLineBytes: ByteArray = System.getProperty("line.separator").toByteArray()

        override fun run() {
            InputStreamReader(input).use { isr ->
                BufferedReader(isr).use { br ->
                    output?.let {
                        var line: String? = br.readLine()
                        while (line != null) {
                            output.write(prefixBytes)
                            output.write(line.toByteArray())
                            output.write(newLineBytes)
                            line = br.readLine()
                        }
                        output.flush()
                    }
                }
            }

            lock.withLock {
                end = true
                condition.signalAll()
            }
        }

        /**
         * Lock 이 풀릴 때까지 기다린다.
         */
        fun waitFor() {
            try {
                lock.withLock {
                    if (!end) {
                        condition.await()
                    }
                }
            } catch (ignored: InterruptedException) {
                // Ignore exception.
            }
        }
    }
}