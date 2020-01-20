package io.kommons.designpatterns.adapter

class App

fun main() {
    val captain = Captain(FishingBoatAdapter())
    captain.row()
}