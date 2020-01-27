package io.kommons.designpatterns.tolerantreader

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object RainbowFishSerializer {

    fun writeV1(rainbowFish: RainbowFish, filename: String) {
        val map = mapOf(
            "name" to rainbowFish.name,
            "age" to rainbowFish.age,
            "lengthMeters" to rainbowFish.lengthMeters,
            "weightTons" to rainbowFish.weightTons
        )

        FileOutputStream(filename).use { fos ->
            ObjectOutputStream(fos).use { oos ->
                oos.writeObject(rainbowFish)
            }
        }
    }

    fun writeV2(rainbowFish: RainbowFishV2, filename: String) {
        FileOutputStream(filename).use { fos ->
            ObjectOutputStream(fos).use { oos ->
                oos.writeObject(rainbowFish)
            }
        }
    }

    fun readV1(filename: String): RainbowFish {
        return FileInputStream(filename).use { fis ->
            ObjectInputStream(fis).use { ois ->
                ois.readObject() as RainbowFish
            }
        }
    }

    fun readV2(filename: String): RainbowFishV2 {
        return FileInputStream(filename).use { fis ->
            ObjectInputStream(fis).use { ois ->
                ois.readObject() as RainbowFishV2
            }
        }
    }
}