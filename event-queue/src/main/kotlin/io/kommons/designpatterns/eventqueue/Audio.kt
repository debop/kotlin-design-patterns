package io.kommons.designpatterns.eventqueue

import io.kommons.logging.KLogging
import io.kommons.logging.trace
import java.io.File
import java.io.IOException
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.LineUnavailableException
import kotlin.concurrent.thread

class Audio {

    companion object: KLogging() {
        const val MAX_PENDING = 16

        private val INSTANCE = Audio()

        fun getInstance(): Audio = INSTANCE
    }

    private var headIndex: Int = 0
    private var tailIndex: Int = 0

    @Volatile
    private var updateThread: Thread? = null

    val pendingAudio: Array<PlayMessage?> = Array(16) { null }

    /**
     * This method stops the Update Method's thread and waits till service stops.
     */
    @Synchronized
    fun stopService() {
        updateThread?.interrupt()
        updateThread?.join()
        updateThread = null
    }

    /**
     * Check the Update Method's thread is started.
     */
    val isServiceRunning: Boolean get() = updateThread?.isAlive ?: false

    /**
     * Starts the thread for the Update Method pattern if it was not started previously. Also when the
     * thread is is ready initializes the indexes of the queue
     */
    fun init() {
        if (updateThread == null) {
            updateThread = thread(start = false) {
                while (!Thread.currentThread().isInterrupted) {
                    update()
                }
            }
        }

        startThread()
    }

    @Synchronized
    private fun startThread() {
        assert(updateThread != null)
        if (!updateThread!!.isAlive) {
            updateThread!!.start()
            headIndex = 0
            tailIndex = 0
        }
    }

    fun playSound(stream: AudioInputStream, volume: Float) {
        init()

        var i = headIndex
        while (i != tailIndex) {
            log.trace { "Get pending audio. index=$i" }
            val playMessage = pendingAudio[i]
            if (playMessage != null && playMessage.stream == stream) {
                playMessage.volume = maxOf(volume, playMessage.volume)
                return
            }
            i = (i + 1) % MAX_PENDING
        }

        pendingAudio[tailIndex] = PlayMessage(stream, volume)
        tailIndex = (tailIndex + 1) % MAX_PENDING
    }

    /**
     * This method uses the Update Method pattern. It takes the audio from the queue and plays it
     */
    private fun update() {
        // If there are no pending requests, do nothing.
        if (headIndex == tailIndex) {
            return
        }
        try {
            val audioStream = pendingAudio[headIndex]!!.stream
            headIndex++
            val clip = AudioSystem.getClip()
            clip.open(audioStream)
            clip.start()
        } catch (e: LineUnavailableException) {
            log.trace { "Error occurred while loading thre audio: The line is unavailable" }
        } catch (e: IOException) {
            log.trace { "Input/Output error while loading the audio" }
        } catch (e: IllegalArgumentException) {
            log.trace { "The system doesn't support the sound: ${e.message}" }
        }
    }

    fun getAudioStream(filePath: String): AudioInputStream =
        AudioSystem.getAudioInputStream(File(filePath).absoluteFile)

}