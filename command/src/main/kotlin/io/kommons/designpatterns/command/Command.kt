package io.kommons.designpatterns.command

interface Command {

    fun execute(target: Target)

    fun undo()

    fun redo()
}