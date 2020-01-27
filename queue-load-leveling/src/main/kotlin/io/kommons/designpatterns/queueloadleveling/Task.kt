package io.kommons.designpatterns.queueloadleveling

interface Task {

    fun submit(msg: Message)
}