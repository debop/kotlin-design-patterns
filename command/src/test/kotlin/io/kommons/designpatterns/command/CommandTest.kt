package io.kommons.designpatterns.command

import io.kommons.designpatterns.command.Size.NORMAL
import io.kommons.designpatterns.command.Size.SMALL
import io.kommons.designpatterns.command.Visibility.INVISIBLE
import io.kommons.designpatterns.command.Visibility.VISIBLE
import io.kommons.designpatterns.command.commands.InvisibilitySpell
import io.kommons.designpatterns.command.commands.ShrinkSpell
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class CommandTest {

    companion object: KLogging() {
        private const val GOBLIN = "Goblin"
    }

    @Test
    fun `run command`() {
        val wizard = Wizard()
        val goblin = Goblin()

        wizard.castSpell(ShrinkSpell(), goblin)
        verifyGoblin(goblin, GOBLIN, SMALL, VISIBLE)

        wizard.castSpell(InvisibilitySpell(), goblin)
        verifyGoblin(goblin, GOBLIN, SMALL, INVISIBLE)

        wizard.undoLastSpell()
        verifyGoblin(goblin, GOBLIN, SMALL, VISIBLE)

        wizard.undoLastSpell()
        verifyGoblin(goblin, GOBLIN, NORMAL, VISIBLE)

        wizard.redoLastSpell()
        verifyGoblin(goblin, GOBLIN, SMALL, VISIBLE)

        wizard.redoLastSpell()
        verifyGoblin(goblin, GOBLIN, SMALL, INVISIBLE)
    }

    private fun verifyGoblin(goblin: Goblin, expectedName: String, expectedSize: Size, expectedVisibility: Visibility) {
        goblin.toString() shouldEqual expectedName
        goblin.size shouldEqual expectedSize
        goblin.visibility shouldEqual expectedVisibility
    }
}