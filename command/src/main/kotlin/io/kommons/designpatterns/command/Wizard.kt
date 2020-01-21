package io.kommons.designpatterns.command

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import io.kommons.logging.info
import java.util.LinkedList

/**
 * Wizard is the invoker of the commands
 */
class Wizard {

    companion object: KLogging()

    private val undoStack = LinkedList<Command>()
    private val redoStack = LinkedList<Command>()

    fun castSpell(command: Command, target: Target) {
        log.info { "$this, casts $command at $target" }
        command.execute(target)
        undoStack.offerLast(command)
    }

    fun undoLastSpell() {
        if (!undoStack.isEmpty()) {
            val lastCommand = undoStack.pollLast()
            redoStack.offerLast(lastCommand)

            log.debug { "$this undoes $lastCommand" }
            lastCommand.undo()
        }
    }

    fun redoLastSpell() {
        if (!redoStack.isEmpty()) {
            val lastCommand = redoStack.pollLast()
            undoStack.offerLast(lastCommand)

            log.debug { "$this redoes $lastCommand" }
            lastCommand.redo()
        }
    }

    override fun toString(): String = "Wizard"
}