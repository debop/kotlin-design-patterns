package io.kommons.designpatterns.eventqueue

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeGreaterThan
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AudioTest {

    private lateinit var audio: Audio

    @BeforeEach
    fun beforeEach() {
        audio = Audio()
    }

    @Test
    fun `play sound`() {
        audio.playSound(audio.getAudioStream("./etc/Bass-Drum-1.wav"), -10.0F)

        audio.isServiceRunning.shouldBeTrue()
        Thread.sleep(5000L)

        audio.stopService()
        audio.isServiceRunning.shouldBeFalse()
    }

    @Test
    fun `test queue`() {
        audio.playSound(audio.getAudioStream("./etc/Bass-Drum-1.aif"), -10.0F)
        audio.playSound(audio.getAudioStream("./etc/Bass-Drum-1.aif"), -10.0F)
        audio.playSound(audio.getAudioStream("./etc/Bass-Drum-1.aif"), -10.0F)

        audio.pendingAudio.size shouldBeGreaterThan 0
        audio.isServiceRunning.shouldBeTrue()

        Thread.sleep(10_000L)

        audio.stopService()
        audio.isServiceRunning.shouldBeFalse()
    }
}