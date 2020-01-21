package io.kommons.designpatterns.command.commands

import io.kommons.designpatterns.command.Command
import io.kommons.designpatterns.command.Size
import io.kommons.designpatterns.command.Target

/**
 * ShrinkSpell is a concrete command
 */
class ShrinkSpell: Command {

    private var oldSize: Size? = null
    private var target: Target? = null

    override fun execute(target: Target) {
        this.oldSize = target.size
        target.size = Size.SMALL
        this.target = target
    }

    override fun undo() {
        if (oldSize != null && target != null) {
            val temp = this.target!!.size
            this.target!!.size = oldSize
            this.oldSize = temp
        }
    }

    override fun redo() {
        undo()
    }

    override fun toString(): String = "Shrink spell"
}