package io.kommons.designpatterns.facade

class App

fun main() {
    DwarvenGoldmineFacade().apply {
        startNewDay()
        digOutGold()
        endDay()
    }
}