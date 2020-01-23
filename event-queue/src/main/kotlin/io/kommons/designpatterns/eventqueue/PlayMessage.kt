package io.kommons.designpatterns.eventqueue

import javax.sound.sampled.AudioInputStream

class PlayMessage(var stream: AudioInputStream,
                  var volume: Float = 12.0F)