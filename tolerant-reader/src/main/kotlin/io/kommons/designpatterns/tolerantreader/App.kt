package io.kommons.designpatterns.tolerantreader

import io.kommons.logging.KotlinLogging
import io.kommons.logging.info
import java.nio.file.Files
import java.nio.file.Paths

class App

private val log = KotlinLogging.logger { }


fun main() {
    // Write V1
    val fishV1 = RainbowFish("Zed", 10, 11, 12)
    log.info { "fishV1=$fishV1" }
    RainbowFishSerializer.writeV1(fishV1, "fish1.out")

    // Read V1
    val deserializedFishV1 = RainbowFishSerializer.readV1("fish1.out")
    log.info { "deserializedFishV1=$deserializedFishV1" }

    // Write V2
    val fishV2 = RainbowFishV2("Scar", 5, 12, 15, true, true, true)
    log.info { "fishV2=$fishV2" }
    RainbowFishSerializer.writeV2(fishV2, "fish2.out")

    // Read V2
    val deserializedFishV2 = RainbowFishSerializer.readV2("fish2.out")
    log.info { "deserializedFishV2=$deserializedFishV2" }

    Files.deleteIfExists(Paths.get("fish1.out"))
    Files.deleteIfExists(Paths.get("fish2.out"))
}