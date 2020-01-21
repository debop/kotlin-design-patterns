package io.kommons.designpatterns.command.commands

import io.kommons.designpatterns.command.Command
import io.kommons.designpatterns.command.Target
import io.kommons.designpatterns.command.Visibility

/**
 * InvisibilitySpell is a concrete command
 */
class InvisibilitySpell: Command {

    private var target: Target? = null

    override fun execute(target: Target) {
        target.visibility = Visibility.INVISIBLE
        this.target = target
    }

    override fun undo() {
        target?.visibility = Visibility.VISIBLE
    }

    override fun redo() {
        target?.visibility = Visibility.INVISIBLE
    }

    override fun toString(): String = "Invisibility spell"
}