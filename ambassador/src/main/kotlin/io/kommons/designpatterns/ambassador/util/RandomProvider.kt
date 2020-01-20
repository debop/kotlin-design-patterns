package io.kommons.designpatterns.ambassador.util

interface RandomProvider {

    fun random(): Double

}

fun randomProviderOf(nextDouble: () -> Double): RandomProvider =
    object: RandomProvider {
        override fun random(): Double = nextDouble()
    }